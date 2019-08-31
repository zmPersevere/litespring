package org.litespring.context.support;

import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.context.ApplicationContext;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 18:00 2019-08-31
 */
public class ClassPathXmlApplicationContext extends ApplicationContext {

    public DefaultBeanFactory factory;

    public ClassPathXmlApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader( factory );
        reader.loadBeanDefinitions( configFile );
    }

    @Override
    public Object getBean(String beanId) {
        return this.factory.getBean( beanId );
    }
}
