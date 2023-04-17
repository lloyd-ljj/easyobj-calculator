package org.huanyuan.easyobj.calculator.core.engine;

import lombok.Data;
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
@Data
public class CalculateContext<T, U, V extends Condition> {

    private Target<T> target;

    private Result<U> result;

    private CalculateStatusEnum status;

    private List<Step<V>> stepList;

    private int currentStep;
}
