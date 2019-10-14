package org.litespring.context.support;

import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.context.ApplicationContext;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;
import org.litespring.util.ClassUtils;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 18:43 2019-09-01
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    public DefaultBeanFactory factory = null;

    private ClassLoader beanClassLoader;

    public AbstractApplicationContext(String configFile) {
        //这里只是把litespring_02中的测试用例拿过来，同时支持了多种resource;
        //利用模版模式消除重复代码。
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader( factory );
        Resource resource = this.getResourceByPath( configFile );
        reader.loadBeanDefinitions( resource );
        //设置类加载器
        factory.setBeanClassLoader( this.getBeanClassLoader() );
    }

    @Override
    public Object getBean(String beanId) {
        return this.factory.getBean( beanId );
    }

    protected abstract Resource getResourceByPath(String path);

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return (this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
    }

}
