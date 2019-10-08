package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.util.ClassUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description : bean工厂的默认实现
 * @Author : zhangMing
 * @Date : Created in 9:16 PM 2019/5/9
 */
public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
        implements ConfigurableBeanFactory, BeanDefinitionRegistry{

    private ClassLoader beanClassLoader;

    private final Map<String,BeanDefinition> beanDefinitionMap =
            new ConcurrentHashMap<String, BeanDefinition>();

    public void registerBeanDefinition(String id, BeanDefinition bd) {
        this.beanDefinitionMap.put(id,bd);
    }

    public BeanDefinition getBeanDefinition(String BeanId) {
        return this.beanDefinitionMap.get(BeanId);
    }

    /**
     * 通过BeanId获取bean
     * @param beanId
     * @return
     */
    public Object getBean(String beanId) {
        //获取bean的定义
        BeanDefinition bd = this.getBeanDefinition(beanId);
        if (bd == null){
            return null;
        }
/*        //获取默认类加载器
        ClassLoader cl = this.getBeanClassLoader();
        //获取bean的name
        String beanClassName = bd.getBeanClassName();
        try {
            Class<?> clazz = cl.loadClass(beanClassName);
            //反射出类的实体
            return clazz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for " + beanClassName + " fail");
        }*/
        //判断要创建的bean是不是单例的。
        if (bd.isSingleton()){
            //先从singletonObjects这个map中获取
            Object bean = this.getSingleton( beanId );
            //如果没有获取到则创建一个，同时放进singletonObjects中
            if (null == bean){
                bean = createBean(bd);
                this.registerSingleton( beanId , bean );
            }
            return bean;
        }
        //直接创建一个bean
        return createBean(bd);
    }

    /**
     * 通过BeanDefintion创建bean
     * @param bd
     * @return
     */
    private Object createBean(BeanDefinition bd) {
        //获取默认类加载器
        ClassLoader cl = this.getBeanClassLoader();
        //获取bean的name
        String beanClassName = bd.getBeanClassName();
        try {
            Class<?> clazz = cl.loadClass(beanClassName);
            //反射出类的实体
            return clazz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for " + beanClassName + " fail");
        }
    }


    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return (this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
    }
}
