package org.litespring.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 17:32 2019-11-18
 */
public interface Advice extends MethodInterceptor {

    public Pointcut getPointcut();

}
