package org.litespring.aop;

import java.lang.reflect.Method;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 16:37 2019-11-16
 */
public interface MethodMatcher {

    abstract boolean matches(Method method/*, Class<?> targetClass*/);
}
