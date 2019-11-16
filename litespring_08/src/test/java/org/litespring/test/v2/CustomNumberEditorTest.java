package org.litespring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.propertyeditors.CustomNumberEditor;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 7:49 PM 2019/10/9
 */
public class CustomNumberEditorTest {

    @Test
    public void testConvertString(){
        CustomNumberEditor editor = new CustomNumberEditor(Integer.class,true);
        editor.setAsText("3");
        Object value = editor.getValue();
        Assert.assertTrue(value instanceof Integer);
        Assert.assertEquals(3,((Integer) editor.getValue()).intValue());

        editor.setAsText("");
        Assert.assertTrue(editor.getValue() == null);
        try {
            editor.setAsText("3.1");
        }catch (IllegalArgumentException e){
            return;
        }
        Assert.fail();

    }
}
