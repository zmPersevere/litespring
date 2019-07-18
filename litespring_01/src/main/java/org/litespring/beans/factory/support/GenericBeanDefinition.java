package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;

/**
 * @Description : bean的定义的实现
 * @Author : zhangMing
 * @Date : Created in 9:33 PM 2019/5/9
 */
public class GenericBeanDefinition implements BeanDefinition{

    private String id;
    private String beanClassName;

    public GenericBeanDefinition(String id,String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }

    /**
     * 获取bean定义中的className
     * @return className
     */
    public String getBeanClassName() {
        return this.beanClassName;
    }

}
