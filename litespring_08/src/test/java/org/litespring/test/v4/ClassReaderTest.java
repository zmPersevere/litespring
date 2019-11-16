package org.litespring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.core.annotation.AnnotationAttributes;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.type.classreading.AnnotationMetadataReadingVisitor;
import org.litespring.core.type.classreading.ClassMetadataReadingVisitor;
import org.springframework.asm.ClassReader;

import java.io.IOException;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 8:17 PM 2018/12/26
 */
public class ClassReaderTest {

    /**
     * 测试asm解析类
     * @throws IOException
     */
    @Test
    public void testGetClassMetaData()throws IOException{
        //根据class生成resource
        ClassPathResource resource = new ClassPathResource("org/litespring/service/v4/PetStoreService.class");
        //通过classReader读取字节流
        ClassReader reader = new ClassReader(resource.getInputStream());
        //实现自己的Vistitor为了让Asm把解析好的类通过ClassReader回调Vistitor的方式通知我们
        ClassMetadataReadingVisitor visitor = new ClassMetadataReadingVisitor();
        //ClassReader回调创建的这个visitor
        reader.accept(visitor , ClassReader.SKIP_DEBUG);
        Assert.assertFalse(visitor.isAbstract());
        Assert.assertFalse(visitor.isInterface());
        Assert.assertFalse(visitor.isFinal());
        Assert.assertEquals("org.litespring.service.v4.PetStoreService", visitor.getClassName());
        Assert.assertEquals("java.lang.Object", visitor.getSuperClassName());
        Assert.assertEquals(0, visitor.getInterfaceNames().length);
    }

    /**
     * 测试asm解析注解
     * @throws Exception
     */
    @Test
    public void testGetAnnonation() throws Exception{
        ClassPathResource resource = new ClassPathResource("org/litespring/service/v4/PetStoreService.class");
        ClassReader reader = new ClassReader(resource.getInputStream());
        //注解的visitor
        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();
        reader.accept(visitor, ClassReader.SKIP_DEBUG);
        String annotation = "org.litespring.stereotype.Component";
        //判断vistor取得的注解
        Assert.assertTrue(visitor.hasAnnotation(annotation));
        //获取vistor取得注解的实体
        AnnotationAttributes attributes = visitor.getAnnotationAttributes(annotation);

        Assert.assertEquals("petStore", attributes.get("value"));

    }
}
