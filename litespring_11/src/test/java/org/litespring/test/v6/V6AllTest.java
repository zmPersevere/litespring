package org.litespring.test.v6;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.litespring.test.v5.*;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 17:11 2019-11-20
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ ApplicationContextTest5.class, CglibAopProxyTest.class, CGLibTest.class,
        MethodLocatingFactoryTest.class, PointcutTest.class, ReflectiveMethodInvocationTest.class })
public class V5AllTest {
}
