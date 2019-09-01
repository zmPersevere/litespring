package org.litespring.beans.factory.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.GenericBeanDefinition;
import org.litespring.core.io.Resource;
import org.litespring.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * @Description : xml解析类
 * @Author : zhangMing
 * @Date : Created in 16:35 2019-08-10
 */
public class XmlBeanDefinitionReader {

    public static final String ID_ATTRIBUTE = "id";
    public static final String CLASS_ATTRIBUTE = "class";

    BeanDefinitionRegistry registry;//bean的注册实体

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry){
        this.registry = registry;
    }

    public void loadBeanDefinitions(Resource resource ){
        InputStream is = null;
        try {
            is = resource.getInputStream();
/*            //获取默认类加载器
            ClassLoader cl = ClassUtils.getDefaultClassLoader();
            //读取文件
            is = cl.getResourceAsStream(configFile);*/
            SAXReader reader = new SAXReader();
            Document doc = null;
            doc = reader.read(is);
            // 获取<beans>
            Element root = doc.getRootElement();
            Iterator<Element> iterator = root.elementIterator();
            //遍历所有<bean>,并把信息注册到registry中
            while (iterator.hasNext()){
                Element element = (Element)iterator.next();
                String id = element.attributeValue(ID_ATTRIBUTE);
                String beanClassName = element.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition bd = new GenericBeanDefinition(id,beanClassName);
                this.registry.registerBeanDefinition(id,bd);
            }
        } catch (DocumentException | IOException e) {
            throw new BeanDefinitionStoreException("IOException parsing XML document",e);
        } finally {
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }





}
