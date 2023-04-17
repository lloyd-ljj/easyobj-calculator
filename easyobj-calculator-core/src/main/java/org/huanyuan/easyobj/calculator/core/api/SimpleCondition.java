package org.huanyuan.easyobj.calculator.core.api;

import org.huanyuan.easyobj.calculator.core.common.R;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-14 19:09
 */
public class SimpleCondition extends Condition {

    private static final String DEFAULT_CONTENT_NAME = "DEFAULT_CONTENT_NAME";

    public SimpleCondition(String condition) {
        super();
        this.condition.put(R.<String>of(DEFAULT_CONTENT_NAME), condition);
    }

    public String get() {
        return super.get(R.<String>of(DEFAULT_CONTENT_NAME));
    }
}
