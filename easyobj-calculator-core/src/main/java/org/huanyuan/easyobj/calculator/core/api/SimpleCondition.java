package org.huanyuan.easyobj.calculator.core.api;

import org.huanyuan.easyobj.calculator.core.common.R;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-14 19:09
 */
public class SimpleCondition extends Condition {

    private static final long serialVersionUID = 1284537750158325399L;

    private static final String DEFAULT_CONTENT_NAME = "DEFAULT_CONTENT_NAME";

    public SimpleCondition(String ruleType, String condition) {
        super(ruleType);
        this.condition.put(R.<String>of(DEFAULT_CONTENT_NAME), condition);
    }

    public static SimpleCondition of(String ruleType, String condition) {
        return new SimpleCondition(ruleType, condition);
    }

    public String get() {
        return super.get(R.<String>of(DEFAULT_CONTENT_NAME));
    }
}
