package org.litespring.beans.factory.support;

import com.sun.xml.internal.bind.v2.model.core.ID;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description : bean工厂的默认实现
 * @Author : zhangMing
 * @Date : Created in 9:16 PM 2019/5/9
 */
public class DefaultBeanFactory implements BeanFactory {

    private static final String ID_ATTRIBUTE = "id";

    private static final String CLASS_ATTRIBUTE = "class";

    private final Map<String,BeanDefinition> beanDefinitionMap =
            new ConcurrentHashMap<String, BeanDefinition>();

    public DefaultBeanFactory(String configFile) {
        loadBeanDefinition(configFile);
    }

    /**
     * 加载配置文件
     * @param configFile
     */
    private void loadBeanDefinition(String configFile){
        InputStream is = null;
        try {
            //获取默认的ClassLoader
            ClassLoader cl = ClassUtils.getDefaultClassLoader();
            //根据dom4j读取configFile
            is = cl.getResourceAsStream(configFile);
            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);
            Element root = doc.getRootElement();//获取跟节点<beans>
            Iterator<Element> iter = root.elementIterator();//遍历子节点<bean>
            while (iter.hasNext()){
                Element ele = (Element)iter.next();
                String id = ele.attributeValue(ID_ATTRIBUTE);//获取<bean id>
                String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);//获取<bean class>
                //把id、class都注册到bean的定义中
                BeanDefinition bd = new GenericBeanDefinition(id,beanClassName);
                //把bean的定义放入到并发安全容器beanDefinitionMap中
                this.beanDefinitionMap.put(id,bd);
            }
        }catch (DocumentException e){
            //TODO 抛出异常,不推荐使用e.printStackTrace()，尽量用日志输出异常。
            throw new BeanDefinitionStoreException("IOException parsing XML document" , e);
        }finally {
            if (is != null){
                 try {
                     is.close();//关闭流
                 }catch (IOException e){
                     e.printStackTrace();
                 }
            }
        }
    }

    /**
     * 通过beanId获取bean的定义
     * @param beanId
     * @return
     */
    public BeanDefinition getBeanDefinition(String beanId) {
        return this.beanDefinitionMap.get(beanId);
    }

    /**
     * 通过BeanId获取bean
     * @param beanId
     * @return
     */
    public Object getBean(String beanId) {
        //根据bean的id获取bean的定义
        BeanDefinition bd = this.getBeanDefinition(beanId);
        if (bd == null){
            throw new BeanCreationException("Bean Definition does not exist");
        }
        //注意这里使用的是ClassLoader，ClassLoader只是把class加载进来并没有初始化，在使用时在进行初始化。
        //而Class.forName会把class加载、连接、初始化。
        ClassLoader cl = ClassUtils.getDefaultClassLoader();
        //这里获取的实际上就是<bean class="">中的class内容
        String beanClassName = bd.getBeanClassName();
        try {
            Class<?> clz = cl.loadClass(beanClassName);
            //只能调用无参构造函数且是publish的.
            //newInstance必须保证这个类已经被加载、连接了。
            return clz.newInstance();
        }catch (Exception e){
            throw new BeanCreationException("create bean for " + beanClassName + " failed ", e);
        }
    }
}
