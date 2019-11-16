package org.litespring.beans;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 8:46 PM 2018/12/11
 */
public class BeansException extends RuntimeException {

    public BeansException(String msg){
        super(msg);
    }

    public BeansException(String msg, Throwable cause){
        super(msg,cause);
    }
}
