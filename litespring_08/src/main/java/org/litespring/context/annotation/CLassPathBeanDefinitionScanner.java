package org.litespring.context.annotation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.BeanNameGenerator;
import org.litespring.core.io.Resource;
import org.litespring.core.io.support.PackageResourceLoader;
import org.litespring.core.type.classreading.MetadataReader;
import org.litespring.core.type.classreading.SimpleMetadataReader;
import org.litespring.stereotype.Component;
import org.litespring.util.StringUtils;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 19:19 2019-10-29
 */
public class CLassPathBeanDefinitionScanner {

    private final BeanDefinitionRegistry registry;

    private PackageResourceLoader resourceLoader = new PackageResourceLoader();

    protected final Log looger = LogFactory.getLog(getClass());

    private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

    public CLassPathBeanDefinitionScanner(BeanDefinitionRegistry registry){
        this.registry = registry;
    }

    /**
     * 对包进行扫描，把包内的class转换成BeanDefinition.同时注册到beanDefinitionMap中
     * @param packagesToScan
     * @return
     */
    public Set<BeanDefinition> doScan(String packagesToScan){
        //对包进行转换。可能存在多个，使用"，"进行分割，转换成数组
        String[] basePackages = StringUtils.tokenizeToStringArray(packagesToScan , ",");
        Set<BeanDefinition> beanDefinitions = new LinkedHashSet<BeanDefinition>();
        //对数组进行遍历
        for (String basePackage : basePackages){
            //找到包下带有Components注解的class，放入到candidates中
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for(BeanDefinition candidate : candidates ){
                //放入beanDefinitions中
                beanDefinitions.add(candidate);
                //注册到BeanDefinitionMap中
                registry.registerBeanDefinition(candidate.getId(),candidate);
            }
        }
        return beanDefinitions;

    }

    /**
     * 找到包下带有Components注解的class
     * @param basePackage
     * @return
     */
    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<BeanDefinition>();
        try {
            //把包下的所有类转换成resource数组
            Resource[] resources = this.resourceLoader.getResources(basePackage);
            for (Resource resource : resources) {
                try {
                    //通过对Asm封装的MetadataReader对每一个resource进行解析
                    MetadataReader metadataReader = new SimpleMetadataReader(resource);
                    //判断没一个resource是否由Component注解
                    if(metadataReader.getAnnotationMetadata().hasAnnotation( Component.class.getName())){
                        //把bean转换成一个ScannedGenericBeanDefinition，它与GenericBeanDefinition类似，是其一个子类。
                        ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader.getAnnotationMetadata());
                        String beanName = this.beanNameGenerator.generateBeanName(sbd, this.registry);
                        sbd.setId(beanName);
                        candidates.add(sbd);
                    }
                }
                catch (Throwable ex) {
                    throw new BeanDefinitionStoreException(
                            "Failed to read candidate component class: " + resource, ex);
                }
            }
        }
        catch (IOException ex) {
            throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
        }
        return candidates;
    }
}

