package org.huanyuan.easyobj.calculator.core.api;

import java.util.Comparator;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-14 19:30
 */
public abstract class AbstractRule<T, U, V extends Condition> implements Rule<T, U, V> {

    protected String name;

    protected String description;

    protected String type;

    protected int priority;

    public AbstractRule(String name, String description, String type, int priority) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.priority = priority;
    }

    @Override
    public boolean evaluate(V condition) {
        return false;
    }

    @Override
    public Result<U> execute(Target<T> target, V condition) throws Exception {
        return null;
    }

    @Override
    public int compareTo(Rule o) {
        if (o.getPriority() == getPriority()) {
            return Comparator.<String>naturalOrder().compare(name, o.getName());
        }
        return o.getPriority() - getPriority();
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
