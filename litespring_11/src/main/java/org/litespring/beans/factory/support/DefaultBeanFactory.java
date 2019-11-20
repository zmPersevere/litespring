package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.BeansException;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.SimpleTypeConverter;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanFactoryAware;
import org.litespring.beans.factory.NoSuchBeanDefinitionException;
import org.litespring.beans.factory.config.BeanPostProcessor;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.beans.factory.config.DependencyDescriptor;
import org.litespring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.litespring.util.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description : bean工厂的默认实现
 * @Author : zhangMing
 * @Date : Created in 9:16 PM 2019/5/9
 */
public class DefaultBeanFactory extends AbstractBeanFactory
        implements BeanDefinitionRegistry{

    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

    private ClassLoader beanClassLoader;

    private final Map<String,BeanDefinition> beanDefinitionMap =
            new ConcurrentHashMap<String, BeanDefinition>();

    public void registerBeanDefinition(String id, BeanDefinition bd) {
        this.beanDefinitionMap.put(id,bd);
    }

    public BeanDefinition getBeanDefinition(String BeanId) {
        return this.beanDefinitionMap.get(BeanId);
    }

    /**
     * 通过BeanId获取bean
     * @param beanId
     * @return
     */
    public Object getBean(String beanId) {
        //获取bean的定义
        BeanDefinition bd = this.getBeanDefinition(beanId);
        if (bd == null){
            return null;
        }
/*        //获取默认类加载器
        ClassLoader cl = this.getBeanClassLoader();
        //获取bean的name
        String beanClassName = bd.getBeanClassName();
        try {
            Class<?> clazz = cl.loadClass(beanClassName);
            //反射出类的实体
            return clazz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for " + beanClassName + " fail");
        }*/
        //判断要创建的bean是不是单例的。
        if (bd.isSingleton()){
            //先从singletonObjects这个map中获取
            Object bean = this.getSingleton( beanId );
            //如果没有获取到则创建一个，同时放进singletonObjects中
            if (null == bean){
                bean = createBean(bd);
                this.registerSingleton( beanId , bean );
            }
            return bean;
        }
        //直接创建一个bean
        return createBean(bd);
    }

/*    *//**
     * 通过BeanDefintion创建bean
     * @param bd
     * @return
     *//*
    private Object createBean(BeanDefinition bd) {
        //获取默认类加载器
        ClassLoader cl = this.getBeanClassLoader();
        //获取bean的name
        String beanClassName = bd.getBeanClassName();
        try {
            Class<?> clazz = cl.loadClass(beanClassName);
            //反射出类的实体
            return clazz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for " + beanClassName + " fail");
        }
    }*/

    /**
     * 创建bean
     * @param bd
     * @return
     */
    protected Object createBean(BeanDefinition bd){
        //创建实例
        Object bean = instantiateBean(bd);
        //设置属性
        populateBean(bd,bean);
        //初始化bean
        bean = initializeBean( bd , bean );
        return bean;
    }

    /**
     * 初始化bean
     * @param bd
     * @return
     */
    private Object instantiateBean(BeanDefinition bd){
        //判断beanDefintion中是否是根据构造函数构造的。
        if (bd.hasConstructorArgumentValues()){
            ConstructorResolver resolver = new ConstructorResolver(this);
            return resolver.autowireConstructor(bd);
        }else {
            //获取类加载器
            ClassLoader cl = this.getBeanClassLoader();
            String beanClassName = bd.getBeanClassName();
            try {
                //反射出类的实体
                Class<?> clz = cl.loadClass(beanClassName);
                return clz.newInstance();
            } catch (Exception e) {
                throw new BeanCreationException("create bean for " + beanClassName + "failed ", e);
            }
        }
    }

    /**
     * 给bean的属性进行赋值
     * @param bd
     * @param bean
     */
    protected void populateBean(BeanDefinition bd,Object bean){

        for (BeanPostProcessor processor : this.getBeanPostProcessors()){
            if (processor instanceof InstantiationAwareBeanPostProcessor){
                ((InstantiationAwareBeanPostProcessor)processor).postProcessPropertyValues(bean,bd.getId());
            }
        }

        //获取beanDefintion的value
        List<PropertyValue> pvs = bd.getPropertyValues();
        if (pvs == null || pvs.isEmpty()){
            return;
        }
        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);
        SimpleTypeConverter converter = new SimpleTypeConverter();
        try {
            for (PropertyValue pv : pvs){
                String propertyName = pv.getName();
                Object originalValue = pv.getValue();
                //获取具体的实体或值
                Object resolvedValue = valueResolver.resolveValueIfNecessary(originalValue);
                //利用java自带的bean方法，对bean实体属性进行赋值。
                BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
                for (PropertyDescriptor pd : pds){
                    if (pd.getName().equals(propertyName)){
                        //把字符串类型转换成具体的类型
                        Object convertedValue = converter.convertIfNecessary(resolvedValue,pd.getPropertyType());
                        pd.getWriteMethod().invoke(bean,convertedValue);
                        break;
                    }
                }
            }
        }catch (Exception e){
            throw new BeanCreationException("Failed to obtain BeanInfo for class ["+bd.getBeanClassName()+"]");
        }
    }

    protected Object initializeBean(BeanDefinition bd, Object bean)  {
        invokeAwareMethods(bean);
        //Todo，调用Bean的init方法，暂不实现
        if(!bd.isSynthetic()){
            return applyBeanPostProcessorsAfterInitialization(bean,bd.getId());
        }
        return bean;
    }

    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
            throws BeansException {

        Object result = existingBean;
        for (BeanPostProcessor beanProcessor : getBeanPostProcessors()) {
            result = beanProcessor.afterInitialization(result, beanName);
            if (result == null) {
                return result;
            }
        }
        return result;
    }
    private void invokeAwareMethods(final Object bean) {
        if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }
    public List<Object> getBeansByType(Class<?> type){
        List<Object> result = new ArrayList<Object>();
        List<String> beanIDs = this.getBeanIDsByType(type);
        for(String beanID : beanIDs){
            result.add(this.getBean(beanID));
        }
        return result;
    }

    private List<String> getBeanIDsByType(Class<?> type){
        List<String> result = new ArrayList<String>();
        for(String beanName :this.beanDefinitionMap.keySet()){
            if(type.isAssignableFrom(this.getType(beanName))){
                result.add(beanName);
            }
        }
        return result;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return (this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
    }

    /**
     * 查找ioc内的全部bean，注入到属性内
     * @param descriptor
     * @return
     */
    @Override
    public Object resolveDependency(DependencyDescriptor descriptor) {
        Class<?> typeToMatch = descriptor.getDependencyType();//获取属性类型
        for (BeanDefinition bd : this.beanDefinitionMap.values()){//循环遍历所有的Map(IOC的容器)
            //确保BeanDefinition有Class对象
            resolveBeanClass(bd);
            Class<?> beanClass = bd.getBeanClass();//获取BeanDefinition内的class类型
            if (typeToMatch.isAssignableFrom(beanClass)){//如果类型相同，返回这个Bean
                return this.getBean(bd.getId());
            }
        }
        return null;
    }

    public void resolveBeanClass(BeanDefinition bd){
        if (bd.hasBeanClass()){
            return;
        }else {
            try {
                bd.resolveBeanClass(this.getBeanClassLoader());
            }catch (ClassNotFoundException e){
                throw new RuntimeException("can't load Class: " +bd.getBeanClassName());
            }
        }

    }


    @Override
    public void addBeanPostProcessor(BeanPostProcessor postProcessor) {
        this.beanPostProcessors.add(postProcessor);

    }

    @Override
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }


    public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        BeanDefinition bd = this.getBeanDefinition(name);
        if(bd == null){
            throw new NoSuchBeanDefinitionException(name);
        }
        resolveBeanClass(bd);
        return bd.getBeanClass();
    }
}
