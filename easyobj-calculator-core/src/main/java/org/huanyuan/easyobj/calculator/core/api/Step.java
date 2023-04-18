package org.huanyuan.easyobj.calculator.core.api;

import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-13 16:48
 */
@AllArgsConstructor
@NoArgsConstructor
public class Step<T extends Condition> implements Serializable {

    private static final long serialVersionUID = 7170415206946278291L;

    private String type;

    private T condition;

    public Step(Builder<T> builder) {
        this.type = builder.type;
        this.condition = builder.condition;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getCondition() {
        return condition;
    }

    public void setCondition(T condition) {
        this.condition = condition;
    }

    public static <T extends Condition> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T extends Condition> {

        private String type = Rule.DEFAULT_TYPE;

        private T condition = null;

        public Builder<T> builder() {
            return new Builder<T>();
        }

        public Builder<T> type(String type) {
            this.type = type;
            return this;
        }

        public Builder<T> condition(T condition) {
            this.condition = condition;
            return this;
        }

        public Step<T> build() {
            return new Step<T>(this);
        }

    }

}
