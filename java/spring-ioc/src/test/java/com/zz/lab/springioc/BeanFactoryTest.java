package com.zz.lab.springioc;

import com.zz.lab.springioc.service.ServiceListener;
import com.zz.lab.springioc.service.ServiceProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
public class BeanFactoryTest {
    @Test
    public void InjectionByCode() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        MutablePropertyValues listenerProperties = new MutablePropertyValues();
        listenerProperties.add("name", "email");


        AbstractBeanDefinition listenerBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(ServiceListener.class).getBeanDefinition();
        listenerBeanDefinition.setPropertyValues(listenerProperties);

        beanFactory.registerBeanDefinition("serviceListener", listenerBeanDefinition);

        //通过constructor
        ConstructorArgumentValues providerConstructorArgument = new ConstructorArgumentValues();
        providerConstructorArgument.addGenericArgumentValue(listenerBeanDefinition);

        AbstractBeanDefinition providerBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(ServiceProvider.class).getBeanDefinition();
        providerBeanDefinition.setConstructorArgumentValues(providerConstructorArgument);
        beanFactory.registerBeanDefinition("serviceProvider", providerBeanDefinition);

        Assert.assertEquals(beanFactory.getBean(ServiceProvider.class).getServiceListener().getName(), "email");
    }

}
