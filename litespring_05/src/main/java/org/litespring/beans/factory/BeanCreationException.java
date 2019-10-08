package org.litespring.beans.factory;

import org.litespring.beans.BeansException;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 8:46 PM 2018/12/11
 */
public class BeanCreationException extends BeansException {

    private String beanName;

    public BeanCreationException(String msg){
        super(msg);
    }

    public BeanCreationException(String msg, Throwable cause){
        super(msg,cause);
    }

    public BeanCreationException(String beanName , String msg){
        super("Error creating bean with name " + beanName + " : " + msg);
    }

    public BeanCreationException(String beanName , String msg , Throwable cause){
        this(beanName,msg);
        initCause(cause);
    }

    public String getBeanName(){
        return this.beanName;
    }
}
