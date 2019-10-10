package org.litespring.beans.propertyeditors;

import org.litespring.util.NumberUtils;
import org.litespring.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.NumberFormat;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 8:25 PM 2019/10/9
 */
public class CustomNumberEditor extends PropertyEditorSupport {

    private final Class<? extends Number> numberClass;

    private final NumberFormat numberFormat;

    private final boolean allowEmpty;


    /**
     * 构造方法
     * @param numberClass 数据类型
     * @param allowEmpty 是否允许为空
     * @throws IllegalArgumentException
     */
    public CustomNumberEditor(Class<? extends Number> numberClass , boolean allowEmpty)throws IllegalArgumentException{
        this(numberClass,null,allowEmpty);
    }

    /**
     * 构造方法
     * @param numberClass 数据类型
     * @param numberFormat 数据格式
     * @param allowEmpty 是否为空
     * @throws IllegalArgumentException
     */
    public CustomNumberEditor(Class<? extends Number> numberClass, NumberFormat numberFormat ,
                              boolean allowEmpty)throws IllegalArgumentException{

        if (numberClass == null || !Number.class.isAssignableFrom(numberClass)){
            throw new IllegalArgumentException("Property class must be a subclass of Number");
        }
        this.numberClass = numberClass;
        this.numberFormat = numberFormat;
        this.allowEmpty = allowEmpty;
    }

    /**
     * 设置要转换的字符串
     * @param text
     * @throws IllegalArgumentException
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (this.allowEmpty && !StringUtils.hasText(text)){
            setValue(null);
        }else if (this.numberFormat != null){
            setValue(NumberUtils.parseNumber(text, this.numberClass, this.numberFormat));
        }
        else {
            setValue(NumberUtils.parseNumber(text, this.numberClass));
        }
    }

/*    @Override
    public void setValue(Object value) {
        if (value instanceof  Number){
            super.setValue(NumberUtils.convertNumberToTargetClass((Number)value,this.numberClass));
        }else {
            super.setValue(value);
        }
    }*/

/*    @Override
    public String getAsText() {
        Object value = getValue();
        if (value == null){
            return "";
        }
        if (this.numberFormat != null) {
            return this.numberFormat.format(value);
        }else {
            return value.toString();
        }
    }*/
}
