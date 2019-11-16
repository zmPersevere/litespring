package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.context.support.FileSystemXmlApplicationContext;
import org.litespring.service.v1.PetStoreService;

import java.io.File;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 17:55 2019-08-31
 */
public class ApplicationContextTest {

    @Test
    public void testGetBean(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("petstore-v1.xml");
        PetStoreService petStoreService = (PetStoreService)applicationContext.getBean( "petStore" );
        Assert.assertNotNull( petStoreService );
    }

    @Test
    public void testGetBeanFromFileSystemContext(){
        ApplicationContext ctx = new FileSystemXmlApplicationContext("src" + File.separator + "test" + File.separator +"resources" + File.separator + "petstore-v1.xml");
        PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");
        Assert.assertNotNull(petStore);
    }

}
