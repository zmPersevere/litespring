package org.litespring.beans.factory.xml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.aop.config.ConfigBeanDefinitionParser;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.GenericBeanDefinition;
import org.litespring.context.annotation.CLassPathBeanDefinitionScanner;
import org.litespring.core.io.Resource;
import org.litespring.util.StringUtils;

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

    public static final String SCOPE_ATTRIBUTE = "scope";

    public static final String PROPERTY_ELEMENT = "property";

    public static final String REF_ATTRIBUTE = "ref";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String NAME_ATTRIBUTE = "name";

    public static final String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";

    public static final String TYPE_ATTRIBUTE = "type";

    public static final String BEANS_NAMESPACE_URI = "http://www.springframework.org/schema/beans";

    public static final String CONTEXT_NAMESPACE_URI = "http://www.springframework.org/schema/context";

    public static final String AOP_NAMESPACE_URI = "http://www.springframework.org/schema/aop";

    private static final String BASE_PACKAGE_ATTRIBUTE = "base-package";

    protected final Log logger = LogFactory.getLog(getClass());

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
                //获取element的命名空间
                String nameSpaceUri = element.getNamespaceURI();
                if (this.isDefaultNamespace(nameSpaceUri)){
                    //解析默认de注解
                    parseDefaultElement(element);
                }else if (this.isContextNamespace(nameSpaceUri)){
                    //解析context标签de注解，例如扫描包
                    parseComponentElement(element);
                }else if(this.isAOPNamespace(nameSpaceUri)){
                    parseAOPElement(element);  //例如 <aop:config>
                }


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

    private void parseComponentElement(Element element) {
        //获取base-package的value
        //例如<context:component-scan base-package="org.litespring.service.v4,org.litespring.dao.v4">
        String basePackages = element.attributeValue(BASE_PACKAGE_ATTRIBUTE);
        //通过之前写好的CLassPathBeanDefinitionScanner，对这个包进行扫描注册
        CLassPathBeanDefinitionScanner scanner = new CLassPathBeanDefinitionScanner(registry);
        scanner.doScan(basePackages);
    }

    private void parseDefaultElement(Element element) {

        String id = element.attributeValue(ID_ATTRIBUTE);
        String beanClassName = element.attributeValue(CLASS_ATTRIBUTE);
        BeanDefinition bd = new GenericBeanDefinition(id,beanClassName);
        if (null != element.attribute( SCOPE_ATTRIBUTE )){
            bd.setScope( element.attributeValue( SCOPE_ATTRIBUTE ) );
        }
        //进行构造器注入
        parseConstructorArgElements(element,bd);
        //进行set注入
        parsePropertyElement(element,bd);
        this.registry.registerBeanDefinition(id,bd);
        
    }

    private void parseAOPElement(Element ele){
        ConfigBeanDefinitionParser parser = new ConfigBeanDefinitionParser();
        parser.parse(ele, this.registry);
    }

    /**
     * 解析property
     * @param beanElem
     * @param bd
     */
    public void parsePropertyElement(Element beanElem , BeanDefinition bd){
        Iterator iter = beanElem.elementIterator(PROPERTY_ELEMENT);
        //为了让property无顺序性，这里采取了遍历
        while (iter.hasNext()){
            Element propElem = (Element) iter.next();
            String propertyName = propElem.attributeValue(NAME_ATTRIBUTE);
            if (!StringUtils.hasLength(propertyName)) {
                logger.fatal("Tag 'property' must have a 'name' attribute" );
                return;
            }
            Object val = parsePropertyValue(propElem,bd,propertyName);
            PropertyValue pv = new PropertyValue(propertyName,val);
            //给beanDefintion中的propertyValues添加新的pv
            bd.getPropertyValues().add(pv);
        }
    }


    /**
     * 根据ref、value返回对应的实体或值。
     * @param ele
     * @param bd
     * @param propertyName
     * @return
     */
    public Object parsePropertyValue(Element ele , BeanDefinition bd , String propertyName){
        String elementName = (propertyName != null ) ?
                "<property> element for property ' " + propertyName + "'" :
                "<constructor-arg> element ";
        boolean hasRefAttribute = (ele.attribute(REF_ATTRIBUTE) != null);
        boolean hasValueAttribute = (ele.attribute(VALUE_ATTRIBUTE) != null );

        if (hasRefAttribute){
            //如果是ref，则返回RuntimeBeanReference类型
            String refName = ele.attributeValue(REF_ATTRIBUTE);
            if (!StringUtils.hasText(refName)){
                logger.error(elementName + " contains empty 'ref' attribute ");
            }
            RuntimeBeanReference ref = new RuntimeBeanReference(refName);
            return ref;
        }else if (hasValueAttribute){
            //如果是value则返回TypedStringValue类型
            TypedStringValue valueHolder = new TypedStringValue(ele.attributeValue(VALUE_ATTRIBUTE));
            return valueHolder;
        }else {
            throw new RuntimeException(elementName + " must specify a ref or value");
        }
    }

    /**
     * 进行构造器注入
     * @param beanEle
     * @param bd
     */
    public void parseConstructorArgElements(Element beanEle, BeanDefinition bd) {
        Iterator iter = beanEle.elementIterator(CONSTRUCTOR_ARG_ELEMENT);
        while(iter.hasNext()){
            Element ele = (Element)iter.next();
            //xml解析成beanDefintion
            parseConstructorArgElement(ele, bd);
        }

    }

    /**
     * 把xml解析成BeanDefintion中
     * @param ele
     * @param bd
     */
    public void parseConstructorArgElement(Element ele , BeanDefinition bd){
        //获取标签属性
        String typeAttr = ele.attributeValue(TYPE_ATTRIBUTE);
        String nameAttr = ele.attributeValue(NAME_ATTRIBUTE);
        //根据ref、value返回相应类型
        Object value = parsePropertyValue(ele,bd,null);
        //生成valueHolder
        ConstructorArgument.ValueHolder valueHolder = new ConstructorArgument.ValueHolder(value);
        if (StringUtils.hasLength(typeAttr)){
            valueHolder.setType(typeAttr);
        }
        if (StringUtils.hasLength(nameAttr)){
            valueHolder.setName(nameAttr);
        }
        //放到beanDefintion中
        bd.getConstructorArgument().addArgumentValue(valueHolder);
    }

    public boolean isDefaultNamespace(String namespaceUri) {
        return (!StringUtils.hasLength(namespaceUri) || BEANS_NAMESPACE_URI.equals(namespaceUri));
    }
    public boolean isContextNamespace(String namespaceUri){
        return (!StringUtils.hasLength(namespaceUri) || CONTEXT_NAMESPACE_URI.equals(namespaceUri));
    }
    public boolean isAOPNamespace(String namespaceUri){
        return (!StringUtils.hasLength(namespaceUri) || AOP_NAMESPACE_URI.equals(namespaceUri));
    }
}
