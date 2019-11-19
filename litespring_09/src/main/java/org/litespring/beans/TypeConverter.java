package org.litespring.beans;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 19:53 2019-10-09
 */
public interface TypeConverter {

    <T> T convertIfNecessary(Object value, Class<T> requiredType)throws TypeMismatchException;

}
