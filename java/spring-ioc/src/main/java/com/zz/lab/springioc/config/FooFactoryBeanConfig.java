package com.zz.lab.springioc.config;

import com.zz.lab.springioc.FooFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FooFactoryBeanConfig {
    @Bean
    FooFactoryBean getFooFactoryBean() {
        FooFactoryBean factoryBean = new FooFactoryBean();
        factoryBean.setSingleton(true);
        return factoryBean;
    }

    /*@Bean
    BarFactoryBean getBarFactoryBean() {
        BarFactoryBean factoryBean = new BarFactoryBean();
        factoryBean.setSingleton(false);
        return factoryBean;
    }*/
}
