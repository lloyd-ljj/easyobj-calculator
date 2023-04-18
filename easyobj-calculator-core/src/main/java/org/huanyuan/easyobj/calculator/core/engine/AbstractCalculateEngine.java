package org.huanyuan.easyobj.calculator.core.engine;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import org.huanyuan.easyobj.calculator.core.api.*;
import org.huanyuan.easyobj.calculator.core.constant.CalculateStatusEnum;
import org.huanyuan.easyobj.calculator.core.exception.DefaultExceptionHandler;
import org.huanyuan.easyobj.calculator.core.exception.ExceptionHandler;
import org.huanyuan.easyobj.calculator.core.exception.NoSuitableRuleException;

import java.util.List;

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

    abstract <T, V extends Condition, U> void beforeDoExecute(CalculateContext<T, U, V> context);

    abstract <T, V extends Condition, U> void afterDoExecute(CalculateContext<T, U, V> context);

    private void doExecute(CalculateContext context) {
        context.setStatus(CalculateStatusEnum.RUNNING);
        for (Step step : (List<Step>) context.getStepList()) {
            if (CalculateStatusEnum.ERROR.equals(context.getStatus())) {
                return;
            }
            List<Rule> ruleList = ruleMap.getIfPresent(step.getType());
            if (CollectionUtil.isEmpty(ruleList)) {
                context.setStatus(CalculateStatusEnum.RUNNING);
                return;
            }
            boolean rulePassFlag = false;
            for (Rule rule : ruleList) {
                try {
                    if (rule.evaluate(step.getCondition())) {
                        Result result = rule.execute(context.getTarget(), step.getCondition());
                        context.setResult(result);
                        context.setTarget(new Target(result.getResultList()));
                        context.setCurrentStep(context.getCurrentStep() + 1);
                        rulePassFlag = true;
                        break;
                    }
                } catch (Exception e) {
                    context.setStatus(CalculateStatusEnum.ERROR);
                    exceptionHandler.handle(context, e);
                    return;
                }

            }
            if (!rulePassFlag) {
                context.setStatus(CalculateStatusEnum.ERROR);
                exceptionHandler.handle(context, new NoSuitableRuleException(step));
            }
        }
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
