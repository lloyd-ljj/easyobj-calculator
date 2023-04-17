package org.huanyuan.easyobj.calculator.core.common;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-14 17:08
 */
public class R<T> implements Serializable {

    private String name;

    public static <T> R<T> of(String name) {
        R<T> R = new R<>();
        R.name = name;
        return R;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        R<?> r = (R<?>) o;

        if (Objects.isNull(r.name) ^ Objects.isNull(name)) {
            return false;
        }

        return Objects.equals(name, r.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
