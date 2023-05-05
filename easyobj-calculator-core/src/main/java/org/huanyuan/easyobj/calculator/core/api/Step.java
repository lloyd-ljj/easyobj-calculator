package org.huanyuan.easyobj.calculator.core.api;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import org.huanyuan.easyobj.calculator.core.constant.CalculateOperateEnum;
import org.huanyuan.easyobj.calculator.core.exception.CalculatorException;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-13 16:48
 */
public class Step<T extends Condition> implements Serializable {

    private static final long serialVersionUID = 7170415206946278291L;

    private List<T> condition;

    private List<CalculateOperateEnum> operate;

    public Step(Builder<T> builder) {
        this.condition = builder.condition;
        this.operate = builder.operator;
    }

    public List<CalculateOperateEnum> getOperate() {
        return operate;
    }

    public List<T> getCondition() {
        return condition;
    }

    public static <T extends Condition> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T extends Condition> {

        private List<CalculateOperateEnum> operator = Lists.newArrayList();

        private List<T> condition = Lists.newArrayList();

        public Builder<T> condition(List<T> condition) {
            this.condition = condition;
            return this;
        }

        public Builder<T> operator(List<CalculateOperateEnum> operator) {
            this.operator = operator;
            return this;
        }

        public Step<T> build() {
            if (CollectionUtil.isEmpty(condition)) {
                throw new CalculatorException("condition cannot be empty");
            }
            condition = Objects.isNull(condition) ? Lists.newArrayList() : condition;
            if (condition.size() != operator.size() + 1) {
                throw new CalculatorException("unmatched condition and operate");
            }
            return new Step<T>(this);
        }

    }

}
