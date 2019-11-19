package org.litespring.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.litespring.test.v1.AllV1Test;
import org.litespring.test.v2.V2AllTests;
import org.litespring.test.v3.V3AllTests;
import org.litespring.test.v4.V4AllTest;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 20:07 2019-11-12
 */
@RunWith( Suite.class )
@Suite.SuiteClasses({AllV1Test.class , V2AllTests.class ,
        V3AllTests.class, V4AllTest.class
})
public class AllTest {
}
