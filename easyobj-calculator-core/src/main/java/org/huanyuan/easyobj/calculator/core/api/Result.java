package org.huanyuan.easyobj.calculator.core.api;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-13 15:43
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -460089896446118266L;

    private List<T> result;

    public Result(T result) {
        this.result = Lists.newArrayList(result);
    }

    public Result(List<T> result) {
        this.result = result;
    }

    public static <T> Result<T> of(T result) {
        return new Result<>(result);
    }

    public static <T> Result<T> of(List<T> result) {
        return new Result<>(result);
    }

    public List<T> getResult() {
        return result;
    }
}
