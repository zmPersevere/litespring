package org.litespring.test.v4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.litespring.test.v1.ApplicationContextTest;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 13:27 2019-11-16
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ApplicationContextTest.class,
AutowiredAnnotationProcessorTest.class,ClassPathBeanDefinitionScannerTest.class,
        ClassReaderTest.class,DependencyDescriptorTest.class,
        InjectionMetadataTest.class,MetadataReaderTest.class,
        PackageResourceLoaderTest.class,XmlBeanDefinitionReaderTest.class})
public class V4AllTest {
}
