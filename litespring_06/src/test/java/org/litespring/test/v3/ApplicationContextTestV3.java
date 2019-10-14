package org.litespring.test.v3;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.service.v3.PetStoreService;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 8:38 PM 2018/12/12
 */
public class ApplicationContextTestV3 {

    @Test
    public void testGetBean(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v3.xml");
        PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");
        Assert.assertNotNull(petStore.getAccountDao());
        Assert.assertNotNull(petStore.getItemDao());
        Assert.assertEquals(1,petStore.getVersion());
        System.out.println(petStore.getVersion());
    }


}
