package org.litespring.test.v2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 9:13 PM 2018/12/18
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApplicationContextTestV2.class,
        BeanDefinitionTestV2.class,
        BeanDefinitionValueResolverTest.class,
        CustomBooleaneditorTest.class,
        CustomNumberEditorTest.class,
        TypeConverterTest.class
})
public class V2AllTests {
}
