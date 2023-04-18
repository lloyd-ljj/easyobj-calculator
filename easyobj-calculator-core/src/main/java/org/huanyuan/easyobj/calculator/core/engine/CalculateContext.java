package org.huanyuan.easyobj.calculator.core.engine;

import org.huanyuan.easyobj.calculator.core.api.Condition;
import org.huanyuan.easyobj.calculator.core.api.Result;
import org.huanyuan.easyobj.calculator.core.api.Step;
import org.huanyuan.easyobj.calculator.core.api.Target;
import org.huanyuan.easyobj.calculator.core.constant.CalculateStatusEnum;

import java.util.List;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-13 16:39
 */
public class CalculateContext<T, U, V extends Condition> {

    private Target<T> target;

    private Result<U> result;

    private CalculateStatusEnum status;

    private List<Step<V>> stepList;

    private int currentStep;

    public Target<T> getTarget() {
        return target;
    }

    public void setTarget(Target<T> target) {
        this.target = target;
    }

    public Result<U> getResult() {
        return result;
    }

    public void setResult(Result<U> result) {
        this.result = result;
    }

    public CalculateStatusEnum getStatus() {
        return status;
    }

    public void setStatus(CalculateStatusEnum status) {
        this.status = status;
    }

    public List<Step<V>> getStepList() {
        return stepList;
    }

    public void setStepList(List<Step<V>> stepList) {
        this.stepList = stepList;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }
}
