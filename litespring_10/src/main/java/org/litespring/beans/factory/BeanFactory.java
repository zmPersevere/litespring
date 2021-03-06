package org.litespring.beans.factory;

/**
 * @Description : bean工厂
 * @Author : zhangMing
 * @Date : Created in 9:15 PM 2019/5/9
 */
public interface BeanFactory {

    /**
     * 通过beanID获取bean
     * @param beanId
     * @return
     */
    Object getBean(String beanId);

    Class<?> getType(String name) throws NoSuchBeanDefinitionException;
}
