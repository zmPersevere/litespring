package org.litespring.beans;

import org.litespring.beans.propertyeditors.CustomBooleanEditor;
import org.litespring.beans.propertyeditors.CustomNumberEditor;
import org.litespring.util.ClassUtils;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 19:53 2019-10-09
 */
public class SimpleTypeConverter implements TypeConverter {

    private Map<Class<?> , PropertyEditor> defaultEditors;

    public SimpleTypeConverter(){
    }

    /**
     * 把value转化成具体的实体数据
     * @param value
     * @param requiredType
     * @param <T>
     * @return
     * @throws TypeMismatchException
     */
    @Override
    public <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException {
        if (ClassUtils.isAssignableValue(requiredType,value)){
            return (T)value;
        }else {
            if (value instanceof String ){
                //如果是字符串类型，则根据类型查找要转换成什么数据。
                PropertyEditor editor = findDefaultEditor(requiredType);
                try {
                    editor.setAsText((String)value);
                }catch (IllegalArgumentException e){
                    throw new TypeMismatchException(value,requiredType);
                }
                return (T)editor.getValue();
            }else {
                throw new RuntimeException("Todo : can't convert value for " + value +
                        " class :" +requiredType);
            }
        }
    }

    /**
     * 查询要使用哪个PropertyEditor
     * @param requiredType
     * @return
     */
    private PropertyEditor findDefaultEditor(Class<?> requiredType){
        PropertyEditor editor = this.getDefaultEditor(requiredType);
        if (editor == null){
            throw new RuntimeException("Editor for "+ requiredType + " has not been implemented" );
        }
        return editor;
    }

    /**
     * 根据class返回PropertyEditor
     * @param requiredType
     * @return
     */
    public PropertyEditor getDefaultEditor(Class<?> requiredType) {

        if (this.defaultEditors == null) {
            createDefaultEditors();
        }
        return this.defaultEditors.get(requiredType);
    }

    private void createDefaultEditors() {
        this.defaultEditors = new HashMap<>(64);

        // Spring's CustomBooleanEditor accepts more flag values than the JDK's default editor.
        this.defaultEditors.put(boolean.class, new CustomBooleanEditor(false));
        this.defaultEditors.put(Boolean.class, new CustomBooleanEditor(true));

        // The JDK does not contain default editors for number wrapper types!
        // Override JDK primitive number editors with our own CustomNumberEditor.
		/*this.defaultEditors.put(byte.class, new CustomNumberEditor(Byte.class, false));
		this.defaultEditors.put(Byte.class, new CustomNumberEditor(Byte.class, true));
		this.defaultEditors.put(short.class, new CustomNumberEditor(Short.class, false));
		this.defaultEditors.put(Short.class, new CustomNumberEditor(Short.class, true));*/
        this.defaultEditors.put(int.class, new CustomNumberEditor(Integer.class, false));
        this.defaultEditors.put(Integer.class, new CustomNumberEditor(Integer.class, true));
		/*this.defaultEditors.put(long.class, new CustomNumberEditor(Long.class, false));
		this.defaultEditors.put(Long.class, new CustomNumberEditor(Long.class, true));
		this.defaultEditors.put(float.class, new CustomNumberEditor(Float.class, false));
		this.defaultEditors.put(Float.class, new CustomNumberEditor(Float.class, true));
		this.defaultEditors.put(double.class, new CustomNumberEditor(Double.class, false));
		this.defaultEditors.put(Double.class, new CustomNumberEditor(Double.class, true));
		this.defaultEditors.put(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, true));
		this.defaultEditors.put(BigInteger.class, new CustomNumberEditor(BigInteger.class, true));*/


    }

}
