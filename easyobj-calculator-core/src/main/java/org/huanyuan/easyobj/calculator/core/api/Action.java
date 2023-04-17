package org.huanyuan.easyobj.calculator.core.api;

import java.util.List;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-13 16:18
 */
public interface Action<T, U, V extends Condition> {

   List<U> calculate(T target, V condition) throws Exception;
}
