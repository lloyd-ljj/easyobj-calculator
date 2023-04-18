package org.huanyuan.easyobj.calculator.core.api;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-16 14:26
 */
public class DefaultRule<T, U, V extends Condition> extends AbstractRule<T, U, V> {

    private Decider<V> decider;

    private Action<T, U, V> action;

    public DefaultRule(Builder<T, U, V> builder) {
        super(builder.name, builder.description, builder.type, builder.priority);
        this.decider = builder.decider;
        this.action = builder.action;
    }

    @Override
    public boolean evaluate(V condition) {
        return decider.evaluate(condition);
    }

    @Override
    public Result<U> execute(Target<T> target, V condition) throws Exception {
        List<U> resultList = Lists.newArrayList();
        for (T t : target.getTargetList()) {
            resultList.addAll(action.calculate(t, condition));
        }
        return new Result<>(resultList);
    }

    public static <T, U, V extends Condition> Builder<T, U, V> builder() {
        return new DefaultRule.Builder<>();
    }

    public static class Builder<T, U, V extends Condition> {

        private String name = DEFAULT_NAME;

        private String description = DEFAULT_DESCRIPTION;

        private String type = DEFAULT_TYPE;

        private int priority = DEFAULT_PRIORITY;

        private Decider<V> decider = item -> false;

        private Action<T, U, V> action = (t, c) -> null;

        public Builder<T, U, V> name(String name) {
            this.name = name;
            return this;
        }

        public Builder<T, U, V> description(String description) {
            this.description = description;
            return this;
        }

        public Builder<T, U, V> type(String type) {
            this.type = type;
            return this;
        }

        public Builder<T, U, V> priority(int priority) {
            this.priority = priority;
            return this;
        }

        public Builder<T, U, V> decider(Decider<V> decider) {
            this.decider = decider;
            return this;
        }

        public Builder<T, U, V> action(Action<T, U, V> action) {
            this.action = action;
            return this;
        }

        public DefaultRule<T, U, V> build() {
            return new DefaultRule<T, U, V>(this);
        }

    }
}
