package org.litespring.aop;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 12:28 2019-11-19
 */
public class AopInvocationException extends RuntimeException {

    /**
     * Constructor for AopInvocationException.
     * @param msg the detail message
     */
    public AopInvocationException(String msg) {
        super(msg);
    }

    /**
     * Constructor for AopInvocationException.
     * @param msg the detail message
     * @param cause the root cause
     */
    public AopInvocationException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
