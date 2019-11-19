package org.litespring.context.annotation;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.annotation.AnnotatedBeanDefinition;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.BeanNameGenerator;
import org.litespring.core.annotation.AnnotationAttributes;
import org.litespring.core.type.AnnotationMetadata;
import org.litespring.util.ClassUtils;
import org.litespring.util.StringUtils;

import java.beans.Introspector;
import java.util.Set;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 19:38 2019-10-29
 */
public class AnnotationBeanNameGenerator implements BeanNameGenerator {


    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        if (definition instanceof AnnotatedBeanDefinition) {
            String beanName = determineBeanNameFromAnnotation((AnnotatedBeanDefinition) definition);
            if (StringUtils.hasText(beanName)) {
                // Explicit bean name found.
                return beanName;
            }
        }
        return buildDefaultBeanName(definition, registry);
    }


    protected String determineBeanNameFromAnnotation(AnnotatedBeanDefinition annotatedDef){

        AnnotationMetadata amd = annotatedDef.getMetadata();
        Set<String> types = amd.getAnnotationTypes();
        String beanName = null;
        for (String type : types){
            AnnotationAttributes attributes = amd.getAnnotationAttributes(type);
            if (attributes.get("value") != null){
                Object value = attributes.get("value");
                if (value instanceof String){
                    String strVal = (String)value;
                    if (StringUtils.hasLength(strVal)){
                        beanName = strVal;
                    }
                }
            }
        }
        return beanName;
    }

    protected String buildDefaultBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        return buildDefaultBeanName(definition);
    }


    protected String buildDefaultBeanName(BeanDefinition definition) {
        String shortClassName = ClassUtils.getShortName(definition.getBeanClassName());
        return Introspector.decapitalize(shortClassName);
    }

}
