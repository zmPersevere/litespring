package org.litespring.beans.factory.config;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 11:21 PM 2019/10/7
 */
public class RuntimeBeanReference {

    private final String beanName;

    public RuntimeBeanReference(String beanName){
        this.beanName = beanName;
    }

    public String getBeanName(){
        return this.beanName;
    }
}
