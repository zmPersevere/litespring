package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;

import java.io.File;
import java.io.InputStream;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 18:07 2019-09-01
 */
public class ResourceTest {

    @Test
    public void testClassPathResource() throws Exception{
        Resource r = new ClassPathResource("petstore-v1.xml");
        InputStream is = null ;
        try {
            is = r.getInputStream();
            //这个测试并不充分,应检查具体内容
            Assert.assertNotNull(is);
        }finally {
            if (is != null){
                is.close();
            }
        }
    }

    @Test
    public void testFileSystemResource()throws Exception{
        Resource r = new FileSystemResource("src" + File.separator + "test" + File.separator +"resources" + File.separator + "petstore-v1.xml");
        InputStream is = null;
        try {
            is = r.getInputStream();
            //这个测试并不充分
            Assert.assertNotNull(is);
        }finally {
            if (is != null){
                is.close();
            }
        }
    }
}
