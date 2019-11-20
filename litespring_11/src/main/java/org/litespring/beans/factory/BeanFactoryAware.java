package org.litespring.beans.factory;

import org.litespring.beans.BeansException;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 19:37 2019-11-19
 */
public interface BeanFactoryAware {

    /**
     * Callback that supplies the owning factory to a bean instance.
     * <p>Invoked after the population of normal bean properties
     * but before an initialization callback such as
     * {@link InitializingBean#afterPropertiesSet()} or a custom init-method.
     * @param beanFactory owning BeanFactory (never {@code null}).
     * The bean can immediately call methods on the factory.
     * @throws BeansException in case of initialization errors
     * @see BeanInitializationException
     */
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
