package org.litespring.beans.factory;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 19:37 2019-11-19
 */
public interface FactoryBean<T> {

    T getObject() throws Exception;

    Class<?> getObjectType();

}
