package org.litespring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;
import org.litespring.aop.config.AspectInstanceFactory;

import java.lang.reflect.Method;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 17:34 2019-11-18
 */
public class AspectJBeforeAdvice extends AbstractAspectJAdvice{

    public AspectJBeforeAdvice(Method adviceMethod, AspectJExpressionPointcut pointcut, AspectInstanceFactory adviceObjectFactory){
        super(adviceMethod,pointcut,adviceObjectFactory);
    }

    public Object invoke(MethodInvocation mi) throws Throwable {
        //例如： 调用TransactionManager的start方法
        this.invokeAdviceMethod();
        Object o = mi.proceed();
        return o;
    }


}
