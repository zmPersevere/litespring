package org.litespring.context.support;

import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.context.ApplicationContext;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 18:43 2019-09-01
 */
public abstract class AbstractApplicationContext extends ApplicationContext {

    public DefaultBeanFactory factory = null;

    public AbstractApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader( factory );
        Resource resource = this.getResourceByPath( configFile );
        reader.loadBeanDefinitions( resource );
    }

    @Override
    public Object getBean(String beanId) {
        return this.factory.getBean( beanId );
    }

    protected abstract Resource getResourceByPath(String path);

}
