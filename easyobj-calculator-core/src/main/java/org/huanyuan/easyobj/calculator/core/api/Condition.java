package org.huanyuan.easyobj.calculator.core.api;

import org.huanyuan.easyobj.calculator.core.common.R;
import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-13 20:40
 */
@SuppressWarnings("unchecked")
public class Condition implements Serializable{

    private static final long serialVersionUID = -2753969714220658360L;

    protected Map<R<?>, Object> condition;

    protected String ruleType = Rule.DEFAULT_TYPE;

    public Condition(String ruleType) {
        this.condition = Maps.newConcurrentMap();
        this.ruleType = ruleType;
    }

    public <T> T put(R<T> r, T value) {
        Objects.requireNonNull(r);
        return (T) condition.put(r, value);
    }

    public <T> T remove(R<T> r) {
        Objects.requireNonNull(r);
        return (T) condition.remove(r);
    }

    public <T> T get(R<T> r) {
        Objects.requireNonNull(r);
        return (T) condition.get(r);
    }

    public String getRuleType() {
        return ruleType;
    }
}
