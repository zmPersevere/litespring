package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.PropertyValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description : bean的定义的实现
 * @Author : zhangMing
 * @Date : Created in 9:33 PM 2019/5/9
 */
public class GenericBeanDefinition implements BeanDefinition{

    private String id;

    private Class<?> beanClass;

    private boolean singleton = true;
    private boolean prototype = false;
    private String scope = SCOPE_DEFAULT;

    List<PropertyValue> propertyValues = new ArrayList<PropertyValue>();

    @Override
    public boolean isSingleton() {
        return this.singleton;
    }

    @Override
    public boolean isPrototype() {
        return this.prototype;
    }

    @Override
    public String getScope() {
        return this.scope;
    }

    @Override
    public void setScope(String scope) {
        this.scope = scope;
        this.singleton = SCOPE_SINGLETON.equals(scope) || SCOPE_DEFAULT.equals(scope);
        this.prototype = SCOPE_PROTOTYPE.equals(scope);
    }

    @Override
    public List<PropertyValue> getPropertyValues() {
        return this.propertyValues;
    }


    private String beanClassName;

    public GenericBeanDefinition(String id,String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }

    /**
     * 获取bean定义中的className
     * @return className
     */
    public String getBeanClassName() {
        return this.beanClassName;
    }



}
