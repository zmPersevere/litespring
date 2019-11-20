package org.litespring.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 7:52 PM 2019/10/14
 */
@Target({ElementType.CONSTRUCTOR , ElementType.FIELD , ElementType.METHOD , ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
}
