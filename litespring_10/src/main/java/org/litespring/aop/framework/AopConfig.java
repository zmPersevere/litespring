package org.litespring.aop.framework;

import org.litespring.aop.Advice;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 12:22 2019-11-19
 */
public interface AopConfig {

    Class<?> getTargetClass();

    Object getTargetObject();

    boolean isProxyTargetClass();


    Class<?>[] getProxiedInterfaces();


    boolean isInterfaceProxied(Class<?> intf);


    List<Advice> getAdvices();


    void addAdvice(Advice advice) ;

    List<Advice> getAdvices(Method method/*,Class<?> targetClass*/);

    void setTargetObject(Object obj);

}
