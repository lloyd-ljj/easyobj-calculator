package org.huanyuan.easyobj.calculator.core.exception;

import org.huanyuan.easyobj.calculator.core.api.Condition;
import org.huanyuan.easyobj.calculator.core.engine.CalculateContext;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-18 15:02
 */
public class DefaultExceptionHandler implements ExceptionHandler{

    @Override
    public <T, U, V extends Condition> void handle(CalculateContext<T, U, V> context, Throwable e) {
        if (e instanceof NoSuitableRuleException) {
            throw (NoSuitableRuleException) e;
        }
        if (e instanceof CalculatorException) {
            throw (CalculatorException) e;
        }
        throw new CalculatorException(e);
    }
}
