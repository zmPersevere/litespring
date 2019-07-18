package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.service.v1.PetStoreService;

/**
 * @Description : 以使用者的角度来要求一个beanFactory应该有的功能。
 * @Author : zhangMing
 * @Date : Created in 7:13 PM 2018/12/6
 */
public class BeanFactoryTest {


    @Test
    public void testGetBean(){
        //根据配置文件初始化一个默认工厂
        BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
        //通过beanId获取bean的定义
        BeanDefinition bd = factory.getBeanDefinition("petStore");
        //断言bean的ClassName正确
        Assert.assertEquals("org.litespring.service.v1.PetStoreService",bd.getBeanClassName());
        //根据bean的id获取Bean
        PetStoreService petStore = (PetStoreService) factory.getBean("petStore");
        //断言获取到bean了。
        Assert.assertNotNull(petStore);
    }

    @Test
    public void testInvalidBean(){
        BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
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
            new DefaultBeanFactory("xxxx.xml");
        }catch (BeanDefinitionStoreException e){
            return;
        }
        Assert.fail("expect BeanDefinitionStoreException ");
    }

}
