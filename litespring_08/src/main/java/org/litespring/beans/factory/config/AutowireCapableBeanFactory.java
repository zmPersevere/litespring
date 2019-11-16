package org.litespring.beans.factory.config;

import org.litespring.beans.factory.BeanFactory;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 7:46 PM 2019/1/2
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    public Object resolveDependency(DependencyDescriptor descriptor);
}
