package org.huanyuan.easyobj.calculator.core.builder;

import org.huanyuan.easyobj.calculator.core.api.*;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-16 14:35
 */
public class RuleBuilder<T, U, V extends Condition> {

    private String name = Rule.DEFAULT_NAME;

    private String type = Rule.DEFAULT_TYPE;

    private String description = Rule.DEFAULT_DESCRIPTION;

    private int priority = Rule.DEFAULT_PRIORITY;

    private Decider<V> decider = condition -> false;

    private Action<T, U, V> action = (target, condition) -> null;

    public RuleBuilder<T, U, V> name(String name) {
        this.name = name;
        return this;
    }

    public RuleBuilder<T, U, V> type(String type) {
        this.type = type;
        return this;
    }

    public RuleBuilder<T, U, V> description(String description) {
        this.description = description;
        return this;
    }

    public RuleBuilder<T, U, V> priority(int priority) {
        this.priority = priority;
        return this;
    }

    public RuleBuilder<T, U, V> decider(Decider<V> decider) {
        this.decider = decider;
        return this;
    }

    public RuleBuilder<T, U, V> action(Action<T, U, V> action) {
        this.action = action;
        return this;
    }

    public DefaultRule<T, U, V> build() {
        return new DefaultRule<T, U, V>(name, description, type, priority, decider, action);
    }
}
