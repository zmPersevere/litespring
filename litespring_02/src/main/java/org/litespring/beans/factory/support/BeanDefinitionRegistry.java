package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;

/**
 * @Description : 存储bean的注册信息
 * @Author : zhangMing
 * @Date : Created in 16:42 2019-08-10
 */
public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String id, BeanDefinition bd);

    BeanDefinition getBeanDefinition(String BeanId);

}
