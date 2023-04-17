package org.huanyuan.easyobj.calculator.core.engine;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import org.huanyuan.easyobj.calculator.core.api.Rule;

import java.util.List;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-16 14:51
 */
@SuppressWarnings({"rawtypes"})
public abstract class AbstractCalculateEngine implements CalculateEngine{

    @Override
    public void registerRule(String type, List<Rule> ruleList) {
        ruleList.forEach(rule -> {
            Assert.equals(rule.getType(), type);
        });
        List<Rule> cachedRule = ruleMap.getIfPresent(type);
        if (CollectionUtil.isNotEmpty(cachedRule)) {
            cachedRule.addAll(ruleList);
        } else {
            ruleMap.put(type, ruleList);
        }
    }

    @Override
    public void removeRule(String type, List<Rule> ruleList) {
        ruleList.forEach(rule -> {
            Assert.equals(rule.getType(), type);
        });
        List<Rule> cachedRule = ruleMap.getIfPresent(type);
        if (CollectionUtil.isNotEmpty(cachedRule)) {
            cachedRule.removeAll(ruleList);
        }
    }
}
