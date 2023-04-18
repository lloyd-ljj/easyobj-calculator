package org.huanyuan.easyobj.calculator.core.api;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-13 15:43
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -460089896446118266L;
    
    private List<T> resultList;

    public Result(T result) {
        this.resultList = Lists.newArrayList(result);
    }

    public Result(List<T> result) {
        this.resultList = result;
    }

}
