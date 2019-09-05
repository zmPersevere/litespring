package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;
import org.litespring.service.v1.PetStoreService;

/**
 * @Description : 以使用者的角度来要求一个beanFactory应该有的功能。
 * @Author : zhangMing
 * @Date : Created in 7:13 PM 2018/12/6
 */
public class BeanFactoryTest {

    //根据配置文件初始化一个默认工厂
    DefaultBeanFactory factory = null;
    //实例化xml解析类。
    XmlBeanDefinitionReader reader = null;

    Resource resource = null;

    @Before
    public void setUp(){
        //根据配置文件初始化一个默认工厂
        factory = new DefaultBeanFactory();
        //实例化xml解析类。
        reader = new XmlBeanDefinitionReader( factory );

        resource = new ClassPathResource( "petstore-v1.xml" );
    }


    @Test
    public void testGetBean(){
        //开始解析xml，并把解析结果存入bean的注册类中
        reader.loadBeanDefinitions( resource );
        //通过beanId获取bean的定义
        BeanDefinition bd = factory.getBeanDefinition("petStore");

        Assert.assertTrue(bd.isSingleton());
        Assert.assertFalse(bd.isPrototype());
        Assert.assertEquals(BeanDefinition.SCOPE_DEFAULT,bd.getScope());

        //断言bean的ClassName正确
        Assert.assertEquals("org.litespring.service.v1.PetStoreService",bd.getBeanClassName());
        //根据bean的id获取Bean
        PetStoreService petStore = (PetStoreService) factory.getBean("petStore");
        //断言获取到bean了。
        Assert.assertNotNull(petStore);

        PetStoreService petStore1 = (PetStoreService) factory.getBean("petStore");
        Assert.assertTrue(petStore.equals(petStore1));
    }

    @Test
    public void testInvalidBean(){
        //开始解析xml，并把解析结果存入bean的注册类中
        reader.loadBeanDefinitions(resource);
        try {
            factory.getBean("invalidBean");
        }catch (BeanCreationException e){
            return;
        }
        Assert.fail("expect BeanCreation");
    }

    @Test
    public void testInvalidXML(){
        try {
            resource = new ClassPathResource( "xxx.xml" );
            //开始解析xml，并把解析结果存入bean的注册类中
            reader.loadBeanDefinitions( resource );
        }catch (BeanDefinitionStoreException e){
            return;
        }
        Assert.fail("expect BeanDefinitionStoreException ");
    }

}
