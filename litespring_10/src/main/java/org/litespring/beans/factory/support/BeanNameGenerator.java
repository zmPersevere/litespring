package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 19:32 2019-10-29
 */
public interface BeanNameGenerator {

    String generateBeanName(BeanDefinition beanDefinition , BeanDefinitionRegistry registry);
}