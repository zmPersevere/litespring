package org.litespring.beans.factory.config;

import org.litespring.beans.BeansException;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 7:44 PM 2019/1/11
 */
public interface BeanPostProcessor {

    Object beforeInitialization(Object bean, String beanName) throws BeansException;


    Object afterInitialization(Object bean, String beanName) throws BeansException;
}
