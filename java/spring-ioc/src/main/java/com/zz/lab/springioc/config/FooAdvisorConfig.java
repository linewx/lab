package com.zz.lab.springioc.config;

import com.zz.lab.springioc.aop.FooAdvice;
import com.zz.lab.springioc.aop.FooPointCut;
import com.zz.lab.springioc.entity.Foo;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FooAdvisorConfig {
   /* @Bean
    public DefaultAdvisorAutoProxyCreator getProxy() {
        return new DefaultAdvisorAutoProxyCreator();
    }*/

    @Bean
    public PointcutAdvisor getAdvisor() {
        PointcutAdvisor pointcutAdvisor = new DefaultPointcutAdvisor();
        ((DefaultPointcutAdvisor) pointcutAdvisor).setPointcut(new FooPointCut());
        ((DefaultPointcutAdvisor) pointcutAdvisor).setAdvice(new FooAdvice());

        return pointcutAdvisor;
    }

    @Bean
    public Foo getFoo() {
        return Foo.builder()
                .name("xiaoming")
                .type("male")
                .build();
    }
}
