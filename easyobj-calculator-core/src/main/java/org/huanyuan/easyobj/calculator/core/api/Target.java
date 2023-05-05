package org.huanyuan.easyobj.calculator.core.api;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-13 15:43
 */
public class Target<T> implements Serializable {

    private static final long serialVersionUID = -6155751185855179626L;
    
    private List<T> target;

    public Target(T target) {
        this.target = Lists.newArrayList(target);
    }

    public Target(List<T> target) {
        this.target = target;
    }

    public static <T> Target<T> of(T target) {
        return new Target<>(target);
    }

    public static <T> Target<T> of(List<T> target) {
        return new Target<>(target);
    }

    public List<T> getTarget() {
        return target;
    }

    public void setTarget(List<T> target) {
        this.target = target;
    }
}
