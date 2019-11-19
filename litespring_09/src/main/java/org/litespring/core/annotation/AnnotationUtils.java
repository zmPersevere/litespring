package org.litespring.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 7:39 PM 2019/1/11
 */
public class AnnotationUtils {

    public static <T extends Annotation> T getAnnotation(AnnotatedElement ae, Class<T> annotationType) {
        T ann = ae.getAnnotation(annotationType);
        if (ann == null) {
            for (Annotation metaAnn : ae.getAnnotations()) {
                ann = metaAnn.annotationType().getAnnotation(annotationType);
                if (ann != null) {
                    break;
                }
            }
        }
        return ann;
    }

}
