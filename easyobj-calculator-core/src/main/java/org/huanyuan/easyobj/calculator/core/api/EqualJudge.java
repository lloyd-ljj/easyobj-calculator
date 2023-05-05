package org.huanyuan.easyobj.calculator.core.api;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-19 17:23
 */
public interface EqualJudge<U> {

    boolean equal(U o1, U o2);
}
