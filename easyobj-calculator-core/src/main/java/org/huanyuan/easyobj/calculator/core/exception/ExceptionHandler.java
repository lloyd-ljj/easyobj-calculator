package org.huanyuan.easyobj.calculator.core.exception;

import org.huanyuan.easyobj.calculator.core.api.Condition;
import org.huanyuan.easyobj.calculator.core.engine.CalculateContext;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-18 14:56
 */
public interface ExceptionHandler {

    <T, U, V extends Condition> void handle(CalculateContext<T, U, V> context, Throwable e);
}
