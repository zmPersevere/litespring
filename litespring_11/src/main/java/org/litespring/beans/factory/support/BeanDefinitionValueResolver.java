package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.BeansException;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.FactoryBean;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;

/**
 * @Description : 注入一个Beanfactory，通过resolveValueIfNecessary方法
 * 传递值，根据值的类类型返回具体的内容。如果是RuntimeBeanReference类型返回的是bean
 * 如果是TypedStringValue返回的是字符串
 * @Author : zhangMing
 * @Date : Created in 11:41 AM 2019/10/8
 */
public class BeanDefinitionValueResolver {

    private final AbstractBeanFactory beanFactory;

    public BeanDefinitionValueResolver(AbstractBeanFactory beanFactory){
        this.beanFactory = beanFactory;
    }

    /**
     * 根据propertyValue的值返回具体的内容
     * @param value
     * @return
     */
    public Object resolveValueIfNecessary(Object value){
        if (value instanceof RuntimeBeanReference){
            //如果value的类型为RuntimeBeanReference，则从beanFactory中获取bean
            RuntimeBeanReference ref = (RuntimeBeanReference)value;
            String refName = ref.getBeanName();
            Object bean = this.beanFactory.getBean(refName);
            return bean;
        } else if (value instanceof TypedStringValue){
            //如果value的类型为TypedStringValue，则返回具体的值
            return ((TypedStringValue)value).getValue();
        } else if (value instanceof BeanDefinition) {
            // Resolve plain BeanDefinition, without contained name: use dummy name.
            BeanDefinition bd = (BeanDefinition) value;

            String innerBeanName = "(inner bean)" + bd.getBeanClassName() + "#" +
                    Integer.toHexString(System.identityHashCode(bd));

            return resolveInnerBean(innerBeanName, bd);

        }
        else{
            return value;
        }
    }

    private Object resolveInnerBean(String innerBeanName, BeanDefinition innerBd) {

        try {

            Object innerBean = this.beanFactory.createBean(innerBd);

            if (innerBean instanceof FactoryBean) {
                try {
                    return ((FactoryBean<?>)innerBean).getObject();
                } catch (Exception e) {
                    throw new BeanCreationException(innerBeanName, "FactoryBean threw exception on object creation", e);
                }
            }
            else {
                return innerBean;
            }
        }
        catch (BeansException ex) {
            throw new BeanCreationException(
                    innerBeanName,
                    "Cannot create inner bean '" + innerBeanName + "' " +
                            (innerBd != null && innerBd.getBeanClassName() != null ? "of type [" + innerBd.getBeanClassName() + "] " : "")
                    , ex);
        }
    }
}
