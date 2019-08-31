package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.util.ClassUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description : bean工厂的默认实现
 * @Author : zhangMing
 * @Date : Created in 9:16 PM 2019/5/9
 */
public class DefaultBeanFactory implements BeanFactory , BeanDefinitionRegistry{

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
        //获取默认类加载器
        ClassLoader cl = ClassUtils.getDefaultClassLoader();
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
}
