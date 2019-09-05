package org.litespring.beans;

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

    public boolean isSingleton();

    public boolean isPrototype();

    String getScope();

    void setScope(String scope);
}
