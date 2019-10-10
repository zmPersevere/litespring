package org.litespring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.SimpleTypeConverter;
import org.litespring.beans.TypeConverter;
import org.litespring.beans.TypeMismatchException;

import static org.junit.Assert.fail;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 8:42 PM 2019/10/9
 */
public class TypeConverterTest {

    @Test
    public void testConvertStringToInt(){
        TypeConverter converter = new SimpleTypeConverter();
        Integer i = converter.convertIfNecessary("3",Integer.class);
        Assert.assertEquals(3,i.intValue());
        try {
            converter.convertIfNecessary("3.1",Integer.class);
        }catch (TypeMismatchException e){
            return;
        }
        fail();
    }

    @Test
    public void testConverStringToBoolean(){
        TypeConverter converter = new SimpleTypeConverter();
        Boolean b = converter.convertIfNecessary("true",Boolean.class);
        try {
            converter.convertIfNecessary("xxxxxx",Boolean.class);
        }catch (TypeMismatchException e){
            return;
        }
        fail();
    }
}
