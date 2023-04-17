package org.huanyuan.easyobj.calculator.core.engine;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.huanyuan.easyobj.calculator.core.api.*;

import java.util.List;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-16 14:38
 */
@SuppressWarnings({"rawtypes"})
public interface CalculateEngine {

    Cache<String, List<Rule>> ruleMap = CacheBuilder.newBuilder().build();

    <T, U, V extends Condition> Result<U> execute(T target, List<Step<V>> step);

    void registerRule(String type, List<Rule> ruleList);

    void removeRule(String type, List<Rule> ruleList);

}
