package org.huanyuan.easyobj.calculator.core.engine;

import org.huanyuan.easyobj.calculator.core.api.Condition;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-16 14:54
 */
public class DefaultRuleEngine extends AbstractCalculateEngine {

    @Override
    <T, V extends Condition, U> void beforeDoExecute(CalculateContext<T, U, V> context) {

    }

    @Override
    <T, V extends Condition, U> void afterDoExecute(CalculateContext<T, U, V> context) {

    }
}
