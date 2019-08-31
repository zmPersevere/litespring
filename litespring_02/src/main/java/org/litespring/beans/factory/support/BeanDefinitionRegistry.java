package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;

/**
 * @Description : 存储bean的注册信息
 * @Author : zhangMing
 * @Date : Created in 16:42 2019-08-10
 */
public interface BeanDefinitionRegistry {

    /**
     * 注册bean的定义信息
     * @param id
     * @param bd
     */
    void registerBeanDefinition(String id, BeanDefinition bd);

    /**
     * 获取bean的定义
     * @param BeanId
     * @return
     */
    BeanDefinition getBeanDefinition(String BeanId);

}
