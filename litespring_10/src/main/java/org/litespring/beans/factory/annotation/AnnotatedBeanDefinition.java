package org.litespring.beans.factory.annotation;

import org.litespring.beans.BeanDefinition;
import org.litespring.core.type.AnnotationMetadata;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 19:21 2019-10-29
 */
public interface AnnotatedBeanDefinition extends BeanDefinition {

    AnnotationMetadata getMetadata();
}
