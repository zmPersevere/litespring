package org.litespring.aop.framework;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 12:29 2019-11-19
 */
public class AopConfigException extends RuntimeException {

    /**
     * Constructor for AopConfigException.
     * @param msg the detail message
     */
    public AopConfigException(String msg) {
        super(msg);
    }

    /**
     * Constructor for AopConfigException.
     * @param msg the detail message
     * @param cause the root cause
     */
    public AopConfigException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
