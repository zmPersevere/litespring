package org.litespring.test.v1;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 18:17 2019-08-31
 */
@RunWith( Suite.class )
@Suite.SuiteClasses( {
        ApplicationContextTest.class , BeanFactoryTest.class,
        ResourceTest.class
} )
public class AllV1Test {
}
