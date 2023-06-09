package org.huanyuan.easyobj.calculator.core.exception;

import com.alibaba.fastjson2.JSONObject;
import org.huanyuan.easyobj.calculator.core.api.Condition;
import org.huanyuan.easyobj.calculator.core.api.Step;

import java.text.MessageFormat;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-18 16:00
 */
public class NoSuitableRuleException extends RuntimeException{

    private static final long serialVersionUID = -7540558022254320624L;

    public NoSuitableRuleException(Condition condition) {
        super(MessageFormat.format("no suitable rule: condition {0}",
                JSONObject.toJSONString(condition)));
    }
}
