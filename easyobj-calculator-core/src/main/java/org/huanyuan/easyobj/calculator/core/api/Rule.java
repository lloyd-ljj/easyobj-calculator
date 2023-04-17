package org.huanyuan.easyobj.calculator.core.api;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-13 15:33
 */
public interface Rule<T, U, V extends Condition> extends Comparable<Rule<T, U, V>> {

    String DEFAULT_NAME = "DEFAULT_NAME";

    String DEFAULT_DESCRIPTION = "DEFAULT_DESC";

    String DEFAULT_TYPE = "DEFAULT_TYPE";

    int DEFAULT_PRIORITY = Integer.MAX_VALUE - 1;

    boolean evaluate(V condition);

    Result<U> execute(Target<T> target, V condition) throws Exception;

    int getPriority();

    String getType();

    String getName();

}
