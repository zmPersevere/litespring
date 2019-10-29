package org.litespring.beans;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 19:53 2019-10-09
 */
public class TypeMismatchException extends BeansException{

    private transient Object value;

    private Class<?> requiredType;

    public TypeMismatchException(Object value, Class<?> requiredType) {
        super("Failed to convert value :"+value + "to type "+requiredType);
        this.value = value;
        this.requiredType = requiredType;
    }

    public Object getValue() {
        return value;
    }

    public Class<?> getRequiredType() {
        return requiredType;
    }

}