package org.huanyuan.easyobj.calculator.core.engine;

import cn.hutool.core.collection.CollectionUtil;
import org.huanyuan.easyobj.calculator.core.api.*;
import org.huanyuan.easyobj.calculator.core.constant.CalculateOperateEnum;
import org.huanyuan.easyobj.calculator.core.constant.CalculateStatusEnum;
import org.huanyuan.easyobj.calculator.core.exception.CalculatorException;
import org.huanyuan.easyobj.calculator.core.exception.NoSuitableRuleException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-18 19:55
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class LogicCalculator {

    private final CalculateEngine engine;

    public LogicCalculator(CalculateEngine engine) {
        this.engine = engine;
    }

    public Result calculate(CalculateContext context) throws Exception{
        Step step = (Step) context.getStepList().get(context.getCurrentStep());
        checkParams(step);
        List<CalculateOperateEnum> operateList = (List<CalculateOperateEnum>) step.getOperate();
        List<Condition> conditionList = (List<Condition>) step.getCondition();Deque<Result> resultStack = new ArrayDeque<>();
        Deque<CalculateOperateEnum> operateStack = new ArrayDeque<>();
        resultStack.addLast(doCalculate(conditionList.get(0), context, engine));
        for (int p = 1; p < conditionList.size(); p++) {
            CalculateOperateEnum operate = operateList.get(p - 1);
            switch (operate) {
                case UNION:
                    Result unionResult = union(engine, resultStack.removeLast(), doCalculate(conditionList.get(p), context, engine));
                    resultStack.addLast(unionResult);
                    break;
                case DIFFERENCE:
                case INTERSECTION:
                    resultStack.addLast(doCalculate(conditionList.get(p), context, engine));
                    operateStack.addLast(operate);
                    break;
                default:
                    throw new CalculatorException("invalid operate");
            }
        }
        Result result = resultStack.removeFirst();
        while (resultStack.peekFirst() != null) {
            Result currentResult = resultStack.removeFirst();
            CalculateOperateEnum operate = operateStack.removeFirst();
            switch (operate) {
                case DIFFERENCE:
                    difference(result, currentResult);
                    break;
                case INTERSECTION:
                    intersection(result, currentResult);
                    break;
                default:
                    throw new CalculatorException("invalid operate");
            }
        }
        return result;
    }

    private void intersection(Result rs1, Result rs2) {
        if (CollectionUtil.isEmpty(rs1.getResult())) {
            return;
        }
        if (CollectionUtil.isEmpty(rs2.getResult())) {
            rs1 = Result.of(null);
            return;
        }

        Function<Object, Object> distinctKeyExtractor = (Function<Object, Object>) engine.getEqualKey(rs1.getResult().get(0).getClass());
        Iterator iter = rs1.getResult().iterator();
        while (iter.hasNext()) {
            Object next = iter.next();
            if (!Objects.isNull(distinctKeyExtractor)) {
                boolean tmpFlag = false;
                for (Object item2 : rs2.getResult()) {
                    if (Objects.equals(distinctKeyExtractor.apply(next), distinctKeyExtractor.apply(item2))) {
                        tmpFlag = true;
                        break;
                    } else {
                        if (Objects.equals(next, item2)) {
                            tmpFlag = true;
                            break;
                        }
                    }
                }
                if (!tmpFlag) {
                    iter.remove();
                }
            }
        }
    }

    private void difference(Result rs1, Result rs2) {
        if (CollectionUtil.isEmpty(rs1.getResult()) || CollectionUtil.isEmpty(rs2.getResult())) {
            return;
        }
        Iterator ite = rs1.getResult().iterator();
        while (ite.hasNext()) {
            Object item = ite.next();
            Function<Object, Object> distinctKeyExtractor = (Function<Object, Object>) engine.getEqualKey(rs1.getResult().get(0).getClass());
            if (Objects.isNull(distinctKeyExtractor)) {
                if (rs2.getResult().contains(item)) {
                    ite.remove();
                }
            } else {
                boolean tmpFlag = false;
                for (Object item2 : rs2.getResult()) {
                    if (Objects.equals(distinctKeyExtractor.apply(item), distinctKeyExtractor.apply(item2))) {
                        tmpFlag = true;
                        break;
                    }
                }
                if (tmpFlag) {
                    ite.remove();
                }
            }
        }
    }

    private Result union(CalculateEngine engine, Result rs1, Result rs2) {
        if (CollectionUtil.isEmpty(rs1.getResult()) || CollectionUtil.isEmpty(rs2.getResult())) {
            return Result.of(null);
        }
        if (!Objects.equals(rs1.getResult().get(0).getClass(), rs2.getResult().get(0).getClass())) {
            throw new CalculatorException("different result type can not do relation calculate");
        }
        rs1.getResult().addAll(rs2.getResult());
        Function<Object, Object> distinctKeyExtractor = (Function<Object, Object>) engine.getEqualKey(rs1.getResult().get(0).getClass());
        if (Objects.isNull(distinctKeyExtractor)) {
            return Result.of((List) rs1.getResult().stream().distinct().collect(Collectors.toList()));
        } else {
            return Result.of((List) rs1.getResult().stream().filter(distinctByKey(distinctKeyExtractor)).collect(Collectors.toList()));
        }
    }

    private Result doCalculate(Condition condition, CalculateContext context, CalculateEngine engine) throws Exception{
        if (CalculateStatusEnum.ERROR == context.getStatus()) {
            throw new CalculatorException("error status");
        }
        List<Rule> ruleList = engine.getRule(condition.getRuleType());
        if (CollectionUtil.isEmpty(ruleList)) {
            context.setStatus(CalculateStatusEnum.ERROR);
            throw new NoSuitableRuleException(condition);
        }
        for (Rule rule : ruleList) {
            if (rule.evaluate(condition)) {
                Result result = rule.execute(context.getTarget(), condition);
                if (CollectionUtil.isNotEmpty(result.getResult())) {
                    Function<Object, Object> distinctKeyExtractor = (Function<Object, Object>) engine.getEqualKey(result.getResult().get(0).getClass());
                    if (Objects.nonNull(distinctKeyExtractor)) {
                        result = Result.of((List) result.getResult().stream().filter(distinctByKey(distinctKeyExtractor)).collect(Collectors.toList()));
                    } else {
                        result = Result.of((List) result.getResult().stream().distinct().collect(Collectors.toList()));
                    }
                }
                return result;
            }
        }
        context.setStatus(CalculateStatusEnum.ERROR);
        throw new NoSuitableRuleException(condition);

    }


    private void checkParams(Step step) {
        if (step.getCondition() == null) {
            throw new CalculatorException("condition cannot be empty");
        }
        if (step.getCondition().size() == 1) {
            return;
        }
        if (step.getOperate() == null) {
            throw new CalculatorException("operate cannot be empty");
        }
        if (step.getCondition().size() != step.getOperate().size() + 1) {
            throw new CalculatorException("unmatched condition and operate");
        }
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
