package org.huanyuan.easyobj.calculator.core.engine;

import cn.hutool.core.collection.CollectionUtil;
import org.huanyuan.easyobj.calculator.core.api.*;
import org.huanyuan.easyobj.calculator.core.common.R;
import org.huanyuan.easyobj.calculator.core.constant.CalculateStatusEnum;

import java.util.Iterator;
import java.util.List;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-16 14:54
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class DefaultRuleEngine extends AbstractCalculateEngine {

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
        doExecute(context);
        if (CalculateStatusEnum.END.equals(context.getStatus())) {
            return (Result<U>) context.getResult();
        } else {
            return null;
        }
    }

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
                    e.printStackTrace();
                    context.setStatus(CalculateStatusEnum.ERROR);
                    context.setResult(null);
                    return;
                }

            }
            if (!rulePassFlag) {
                throw new RuntimeException("no suitable rule");
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
