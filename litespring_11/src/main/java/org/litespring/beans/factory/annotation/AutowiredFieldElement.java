package org.litespring.beans.factory.annotation;

import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.config.AutowireCapableBeanFactory;
import org.litespring.beans.factory.config.DependencyDescriptor;
import org.litespring.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 7:32 PM 2019/1/3
 */
public class AutowiredFieldElement extends InjectionElement {

    boolean required;

    public AutowiredFieldElement(Field f, boolean required, AutowireCapableBeanFactory factory) {
        super(f,factory);
        this.required = required;
    }

    public Field getField(){
        return (Field)this.member;
    }

    /**
     * 属性注入
     * @param target
     */
    @Override
    public void inject(Object target) {

        Field field = this.getField();
        try {
            //把属性封装成DependencyDescriptor
            DependencyDescriptor desc = new DependencyDescriptor(field, this.required);
            //从ioc容器找出该属性的对象
            Object value = factory.resolveDependency(desc);
            if (value != null) {//如果找得到，则注入给该属性
                ReflectionUtils.makeAccessible(field);
                field.set(target, value);
            }
        }
        catch (Throwable ex) {
            throw new BeanCreationException("Could not autowire field: " + field, ex);
        }
    }


}
