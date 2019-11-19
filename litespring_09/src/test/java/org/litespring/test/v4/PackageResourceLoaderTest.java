package org.litespring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.core.io.Resource;
import org.litespring.core.io.support.PackageResourceLoader;

import java.io.IOException;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 8:06 PM 2018/12/25
 */
public class PackageResourceLoaderTest {

    @Test
    public void testGetResources() throws IOException{
        PackageResourceLoader loader = new PackageResourceLoader();//扫描包
        //把参数目录进行扫描，同时转成FileSystemResource，存放到Resource[size]里面。
        Resource[] resources = loader.getResources("org.litespring.dao.v4");
        System.out.println(resources.length);
        Assert.assertEquals(2,resources.length);
    }
}
