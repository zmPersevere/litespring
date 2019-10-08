package org.litespring.beans.factory.config;

import org.litespring.beans.factory.BeanFactory;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 19:29 2019-09-02
 */
public interface ConfigurableBeanFactory extends BeanFactory {

    void setBeanClassLoader(ClassLoader classLoader);

    ClassLoader getBeanClassLoader();

}
