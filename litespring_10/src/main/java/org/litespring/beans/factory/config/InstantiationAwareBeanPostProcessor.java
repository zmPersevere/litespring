package org.litespring.beans.factory.config;

import org.litespring.beans.BeansException;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 7:44 PM 2019/1/11
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    boolean afterInstantiation(Object bean, String beanName) throws BeansException;

    void postProcessPropertyValues(Object bean, String beanName)
            throws BeansException;

}
