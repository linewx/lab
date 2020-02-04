package com.zz.lab.springioc.config;

import com.zz.lab.springioc.BarFactoryBean;
import com.zz.lab.springioc.FooFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BarFactoryBeanConfig {
    @Bean
    BarFactoryBean getBarFactoryBean() {
        BarFactoryBean factoryBean = new BarFactoryBean();
        factoryBean.setSingleton(false);
        return factoryBean;
    }
}
