package org.litespring.beans;

import java.util.List;

/**
 * @Description : bean的定义
 * @Author : zhangMing
 * @Date : Created in 9:17 PM 2019/5/9
 */
public interface BeanDefinition {

    public static final String SCOPE_SINGLETON = "singleton";
    public static final String SCOPE_PROTOTYPE = "prototype";
    public static final String SCOPE_DEFAULT = "";

    /**
     * 获取bean定义中的className
     * @return
     */
    String getBeanClassName();

    /**
     * 单例
     * @return
     */
    public boolean isSingleton();

    /**
     * 总是创建一个新的bean
     * @return
     */
    public boolean isPrototype();

    /**
     * 获取scope
     * @return
     */
    String getScope();

    /**
     * 设置scope
     * @param scope
     */
    void setScope(String scope);

    /**
     * 获取property的全部属性
     * @return
     */
    List<PropertyValue> getPropertyValues();

    ConstructorArgument getConstructorArgument();

    String getId();

    boolean hasConstructorArgumentValues();

    public Class<?> resolveBeanClass(ClassLoader classLoader)throws ClassNotFoundException;

    public Class<?> getBeanClass()throws IllegalStateException;
    public boolean hasBeanClass();
}
