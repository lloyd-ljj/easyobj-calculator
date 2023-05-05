package org.huanyuan.easyobj.calculator.core.engine;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import org.huanyuan.easyobj.calculator.core.api.*;
import org.huanyuan.easyobj.calculator.core.constant.CalculateStatusEnum;
import org.huanyuan.easyobj.calculator.core.exception.DefaultExceptionHandler;
import org.huanyuan.easyobj.calculator.core.exception.ExceptionHandler;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-16 14:51
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class AbstractCalculateEngine implements CalculateEngine {

    private ExceptionHandler exceptionHandler = new DefaultExceptionHandler();

    @Override
    public <T, U, V extends Condition> Result<U> execute(T target, List<Step<V>> stepList) {
        CalculateContext<T, U, V> context = initContext(new Target<>(target), stepList);
        doExecute(context);
        if (CalculateStatusEnum.END.equals(context.getStatus())) {
            return context.getResult();
        } else {
            return null;
        }
    }

    public <T, U, V extends Condition> Result<U> execute(T target, List<Step<V>> stepList, Class<U> clazz) {
        CalculateContext<T, U, V> context = initContext(new Target<>(target), stepList);
        beforeDoExecute(context);
        doExecute(context);
        afterDoExecute(context);
        if (CalculateStatusEnum.END.equals(context.getStatus())) {
            return (Result<U>) context.getResult();
        } else {
            return null;
        }
    }

    @Override
    public void registerRule(String type, List<Rule> ruleList) {
        ruleList.forEach(rule -> {
            Assert.equals(rule.getType(), type);
        });
        synchronized (ruleMap) {
            List<Rule> cachedRule = ruleMap.getIfPresent(type);
            if (CollectionUtil.isNotEmpty(cachedRule)) {
                cachedRule.addAll(ruleList);
            } else {
                ruleMap.put(type, ruleList);
            }
        }
    }

    @Override
    public void removeRule(String type, List<Rule> ruleList) {
        ruleList.forEach(rule -> {
            Assert.equals(rule.getType(), type);
        });
        synchronized (ruleMap) {
            List<Rule> cachedRule = ruleMap.getIfPresent(type);
            if (CollectionUtil.isNotEmpty(cachedRule)) {
                cachedRule.removeAll(ruleList);
            }
        }
    }

//    @Override
//    public <T> void registerEqualJudge(Class<T> clazz, EqualJudge<T> equalJudge) {
//        this.equalJudgeMap.put(clazz, equalJudge);
//    }

//    @Override
//    public void removeEqualJudge(Class clazz) {
//        this.equalJudgeMap.invalidate(clazz);
//    }
//
//    @Override
//    public EqualJudge getEqualJudge(Class clazz) {
//        return this.equalJudgeMap.getIfPresent(clazz);
//    }


    @Override
    public <T> void registerEqualKey(Class<T> clazz, Function<? super T, ?> keyExtractor) {
        this.equalKeyMap.put(clazz, keyExtractor);
    }

    @Override
    public <T> void removeEqualKey(Class<T> clazz) {
        this.equalKeyMap.invalidate(clazz);
    }

    @Override
    public <T> Function<? super T, ?> getEqualKey(Class<T> clazz) {
        return this.equalKeyMap.getIfPresent(clazz);
    }

    @Override
    public List<Rule> getRule(String type) {
        return ruleMap.getIfPresent(type);
    }

    abstract <T, V extends Condition, U> void beforeDoExecute(CalculateContext<T, U, V> context);

    abstract <T, V extends Condition, U> void afterDoExecute(CalculateContext<T, U, V> context);

    private void doExecute(CalculateContext context) {
        context.setStatus(CalculateStatusEnum.RUNNING);
        LogicCalculator logicCalculator = new LogicCalculator(this);
        IntStream.range(0, context.getStepList().size()).forEach(idx -> {
            context.setCurrentStep(idx);
            if (CalculateStatusEnum.ERROR.equals(context.getStatus())) {
                return;
            }
            try {
                Result result = logicCalculator.calculate(context);
                context.setTarget(Target.of(result.getResult()));
                context.setResult(result);
            } catch (Exception e) {
                context.setStatus(CalculateStatusEnum.ERROR);
                exceptionHandler.handle(context, e);
            }
        });
        context.setStatus(CalculateStatusEnum.END);
    }

    private <V extends Condition, U, T> CalculateContext<T, U, V> initContext(Target<T> target, List<Step<V>> stepList) {
        CalculateContext<T, U, V> context = new CalculateContext<>();
        context.setTarget(target);
        context.setStatus(CalculateStatusEnum.INIT);
        context.setStepList(stepList);
        context.setCurrentStep(1);
        return context;
    }
}
