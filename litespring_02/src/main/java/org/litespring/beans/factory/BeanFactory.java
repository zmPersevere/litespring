package org.litespring.beans.factory;

import org.litespring.beans.BeanDefinition;

/**
 * @Description : bean工厂
 * @Author : zhangMing
 * @Date : Created in 9:15 PM 2019/5/9
 */
public interface BeanFactory {

    /**
     * 通过beanId获取bean的定义
     * @param beanId
     * @return
     */
    BeanDefinition getBeanDefinition(String beanId);

    /**
     * 通过beanID获取bean
     * @param beanId
     * @return
     */
    Object getBean(String beanId);
}
