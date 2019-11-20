package org.litespring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 17:47 2019-11-18
 */
public class AspectJAfterThrowingAdvice extends AbstractAspectJAdvice {

    public AspectJAfterThrowingAdvice(Method adviceMethod, AspectJExpressionPointcut pointcut, Object adviceObject) {

        super(adviceMethod, pointcut, adviceObject);
    }


    public Object invoke(MethodInvocation mi) throws Throwable {
        try {
            return mi.proceed();
        }
        catch (Throwable t) {
            invokeAdviceMethod();
            throw t;
        }
    }

}
