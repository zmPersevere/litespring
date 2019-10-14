package org.litespring.beans;

import com.sun.istack.internal.NotNull;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 10:59 PM 2019/10/7
 */
public class PropertyValue {

    private final String name;

    @NotNull
    private final Object value;

    private boolean converted = false;

    private Object convertedValue;

    public PropertyValue(String name , Object value){
        this.name = name ;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public Object getValue() {
        return this.value;
    }

    public boolean isConverted() {
        return converted;
    }

    public void setConverted(boolean converted) {
        this.converted = converted;
    }

    public Object getConvertedValue() {
        return convertedValue;
    }

    public void setConvertedValue(Object convertedValue) {
        this.convertedValue = convertedValue;
    }
}
