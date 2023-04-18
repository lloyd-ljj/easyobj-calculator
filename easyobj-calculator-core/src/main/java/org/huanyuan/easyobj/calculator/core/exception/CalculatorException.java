package org.huanyuan.easyobj.calculator.core.exception;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-18 14:55
 */
public class CalculatorException extends RuntimeException{

    private static final long serialVersionUID = 5277557194459346510L;

    public CalculatorException() {
    }

    public CalculatorException(String message) {
        super(message);
    }

    public CalculatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public CalculatorException(Throwable cause) {
        super(cause);
    }

    public CalculatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
