package org.litespring.beans.factory.config;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 19:14 2019-09-03
 */
public interface SingletonBeanRegistry {

    /**
     * 注册单例
     * @param beanName
     * @param singletonObject
     */
    void registerSingleton(String beanName,Object singletonObject);

    /**
     * 获取单例的bean
     * @param beanName
     */
    Object getSingleton(String beanName);
}
