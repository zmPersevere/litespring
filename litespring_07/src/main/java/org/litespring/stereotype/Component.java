package org.litespring.stereotype;

import java.lang.annotation.*;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 7:50 PM 2019/10/14
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {

    String value() default  "";
}
