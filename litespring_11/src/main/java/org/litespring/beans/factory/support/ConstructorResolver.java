package org.litespring.beans.factory.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.SimpleTypeConverter;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * @Description : 找出对应的构造函数，并反射生成出来
 * @Author : zhangMing
 * @Date : Created in 19:57 2019-10-11
 */
public class ConstructorResolver {

    protected final Log logger = LogFactory.getLog(getClass());

    private final AbstractBeanFactory beanFactory;

    public ConstructorResolver(AbstractBeanFactory beanFactory){
        this.beanFactory = beanFactory;
    }

    /**
     * 通过beanDefintion找到具体的构造函数并生成
     * @param bd
     * @return
     */
    public Object autowireConstructor(final BeanDefinition bd){
        Constructor<?> constructorToUse = null ;
        Object[] argsToUse = null ;
        Class<?> beanClass = null ;
        try {
            beanClass = this.beanFactory.getBeanClassLoader().loadClass(bd.getBeanClassName());
        }catch (ClassNotFoundException e){
            throw new BeanCreationException(bd.getId() , "Instantiation of bean failed , can't resolve class" , e);
        }
        //获取该bean的构造函数
        Constructor<?>[] candidates = beanClass.getConstructors();
        //返回具体的类型。根据TypedStringValue或者RuntimeBeanReference
        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this.beanFactory);
        //获取解析后的ConstructorArgument的参数
        ConstructorArgument cargs = bd.getConstructorArgument();
        //返回类型
        SimpleTypeConverter typeConverter = new SimpleTypeConverter();
        //查询对应的构造函数
        for (int i = 0 ; i < candidates.length ; i ++){
            Class<?>[] paramterTypes = candidates[i].getParameterTypes();
            //如果参数数量不对 就继续下一轮查找
            if (paramterTypes.length != cargs.getArgumentCount()){
                continue;
            }
            argsToUse = new Object[paramterTypes.length];
            //参数数量对应上了，判断类型是否一致
            boolean result = this.valuesMatchTypes(paramterTypes,cargs.getArgumentValues(), argsToUse,
                    valueResolver, typeConverter);
            if (result){
                constructorToUse = candidates[i];
                break;
            }
        }

        //找不到一个合适的构造函数
        if(constructorToUse == null){
            throw new BeanCreationException(bd.getId() , "can't find a apporiate construvtor ");
        }

        try {
            //返回通过构造函数实例化的bean
            return constructorToUse.newInstance(argsToUse);
        }catch (Exception e){
            throw new BeanCreationException(bd.getId() , "can't find a create instance useing " + constructorToUse);
        }
    }


    /**
     * 判断构造器是否符合
     * @param parameterTypes
     * @param valueHolders
     * @param argsToUse
     * @param valueResolver
     * @param typeConverter
     * @return
     */
    private boolean valuesMatchTypes(Class<?> [] parameterTypes,
                                     List<ConstructorArgument.ValueHolder> valueHolders,
                                     Object[] argsToUse,
                                     BeanDefinitionValueResolver valueResolver,
                                     SimpleTypeConverter typeConverter ){
        for (int i = 0 ; i < parameterTypes.length ; i ++){
            ConstructorArgument.ValueHolder valueHolder = valueHolders.get(i);
            //获取参数的值，可能是TypedStringValue,也可能是RuntimeBeanReference
            Object originalValue = valueHolder.getValue();

            try {
                //获取真正的值
                Object resolvedValue = valueResolver.resolveValueIfNecessary(originalValue);
                //如果参数类型是int，但是值是字符串，例如3，还需要转型
                //如果转型失败，则抛出异常。说明这个构造函数不可用
                Object convertedValue = typeConverter.convertIfNecessary(resolvedValue,parameterTypes[i]);
                //转型成功，记录下来
                argsToUse[i] = convertedValue;
            }catch (Exception e){
                logger.error(e);
                return false;
            }

        }
        return true;
    }
}

