package org.huanyuan.easyobj.calculator.core.engine;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.huanyuan.easyobj.calculator.core.api.*;

import java.util.List;
import java.util.function.Function;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-16 14:38
 */
@SuppressWarnings({"rawtypes"})
public interface CalculateEngine {

    Cache<String, List<Rule>> ruleMap = CacheBuilder.newBuilder().build();

//    Cache<Class, EqualJudge> equalJudgeMap = CacheBuilder.newBuilder().build();

    Cache<Class, Function> equalKeyMap = CacheBuilder.newBuilder().build();

    <T, U, V extends Condition> Result<U> execute(T target, List<Step<V>> step);

    void registerRule(String type, List<Rule> ruleList);

    void removeRule(String type, List<Rule> ruleList);

    List<Rule> getRule(String type);

//    <T> void registerEqualJudge(Class<T> clazz, EqualJudge<T> equalJudge);

//    void removeEqualJudge(Class clazz);
//
//    EqualJudge getEqualJudge(Class clazz);


    <T> void registerEqualKey(Class<T> clazz, Function<? super T, ?> keyExtractor);

    <T> Function<? super T, ?> getEqualKey(Class<T> clazz);

    <T> void removeEqualKey(Class<T> clazz);


}
