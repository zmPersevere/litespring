package org.litespring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.factory.config.DependencyDescriptor;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.dao.v4.AccountDao;
import org.litespring.service.v4.PetStoreService;

import java.lang.reflect.Field;

/**
 * @Description : 获取属性的bean测试
 * @Author : zhangMing
 * @Date : Created in 7:36 PM 2019/1/2
 */
public class DependencyDescriptorTest {

    @Test
    public void test()throws Exception{

        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("petstore-v4.xml"));

        //获取类的属性
        Field field = PetStoreService.class.getDeclaredField("accountDao");
        //对属性转为DependencyDescriptor实体，并必须注入
        DependencyDescriptor dependencyDescriptor = new DependencyDescriptor(field,true);
        //获取要注入给该属性的bean
        Object o = factory.resolveDependency(dependencyDescriptor);
        //判断类型
        Assert.assertTrue(o instanceof AccountDao );

    }
}
