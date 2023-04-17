package org.huanyuan.easyobj.calculator.core.api;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-16 14:26
 */
public class DefaultRule<T, U, V extends Condition> extends AbstractRule<T, U, V> {

    private Decider<V> decider;

    private Action<T, U, V> action;

    public DefaultRule(String name, String description, String type, int priority, Decider<V> decider, Action<T, U, V> action) {
        super(name, description, type, priority);
        this.decider = decider;
        this.action = action;
    }

    @Override
    public boolean evaluate(V condition) {
        return decider.evaluate(condition);
    }

    @Override
    public Result<U> execute(Target<T> target, V condition) throws Exception {
        List<U> resultList = Lists.newArrayList();
        for (T t : target.getTargetList()) {
            resultList.addAll(action.calculate(t, condition));
        }
        return new Result<>(resultList);
    }
}
