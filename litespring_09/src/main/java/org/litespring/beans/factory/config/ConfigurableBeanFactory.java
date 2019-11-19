package org.litespring.beans.factory.config;

import org.litespring.beans.factory.BeanFactory;

import java.util.List;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 19:29 2019-09-02
 */
public interface ConfigurableBeanFactory extends AutowireCapableBeanFactory {

    void setBeanClassLoader(ClassLoader classLoader);

    ClassLoader getBeanClassLoader();


    void addBeanPostProcessor(BeanPostProcessor postProcessor);
    List<BeanPostProcessor> getBeanPostProcessors();

}
