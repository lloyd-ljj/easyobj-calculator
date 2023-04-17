//package org.huanyuan.easyobj.calculator.core.builder;
//
//import com.google.common.collect.ImmutableMap;
//import org.huanyuan.easyobj.calculator.core.api.Condition;
//import org.huanyuan.easyobj.calculator.core.api.Rule;
//import org.huanyuan.easyobj.calculator.core.api.Step;
//
///**
// * @Author lianjunjie
// * @Description
// * @Date 2023-04-17 11:13
// */
//public class StepBuilder<T extends Condition> {
//
//    private String type = Rule.DEFAULT_TYPE;
//
//    private T condition = null;
//
//    public StepBuilder() {
//
//    }
//
//    public static <T extends Condition> StepBuilder<T> builder() {
//        return new StepBuilder<T>();
//    }
//
//    public StepBuilder<T> type(String type) {
//        this.type = type;
//        return this;
//    }
//
//    public StepBuilder<T> condition(T condition) {
//        this.condition = condition;
//        return this;
//    }
//
//    public Step<T> build() {
//        return new Step<T>(type, condition);
//    }
//
//
//}
