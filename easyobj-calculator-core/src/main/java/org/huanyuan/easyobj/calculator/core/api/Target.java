package org.huanyuan.easyobj.calculator.core.api;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-13 15:43
 */
@Data
public class Target<T> {

    private List<T> targetList;

    public Target(T target) {
        this.targetList = Lists.newArrayList(target);
    }

    public Target(List<T> targetList) {
        this.targetList = targetList;
    }
}
