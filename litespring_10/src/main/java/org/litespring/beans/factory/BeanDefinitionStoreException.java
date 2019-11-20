package org.litespring.beans.factory;

import org.litespring.beans.BeansException;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 8:46 PM 2018/12/11
 */
public class BeanDefinitionStoreException extends BeansException {

    public BeanDefinitionStoreException(String msg, Throwable cause){
        super(msg,cause);
    }
}
