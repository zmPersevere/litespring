package org.litespring.beans.factory.annotation;

import org.litespring.beans.factory.config.AutowireCapableBeanFactory;

import java.lang.reflect.Member;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 7:20 PM 2019/1/3
 */
public abstract class InjectionElement {

    protected Member member;
    protected AutowireCapableBeanFactory factory;
    InjectionElement(Member member , AutowireCapableBeanFactory factory){
        this.member = member;
        this.factory = factory;
    }

    public abstract void inject(Object object);
}
