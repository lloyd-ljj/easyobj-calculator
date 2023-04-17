package org.huanyuan.easyobj.calculator.core.api;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-13 15:41
 */
public interface Decider<V extends Condition> {

    boolean evaluate(V condition);

}
