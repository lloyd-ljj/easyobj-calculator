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
    
    private List<T> targetList;

    public Target(T target) {
        this.targetList = Lists.newArrayList(target);
    }

    public Target(List<T> targetList) {
        this.targetList = targetList;
    }

    public List<T> getTargetList() {
        return targetList;
    }

    public void setTargetList(List<T> targetList) {
        this.targetList = targetList;
    }
}
