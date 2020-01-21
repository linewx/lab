package com.zz.lab.springioc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRestTemplateConfiguration {
    @Bean
    public MyRestTemplate template1() {
        return new MyRestTemplate();
    }

    @Bean
    @MyLoadBalanced
    public MyRestTemplate template2() {
        return new MyRestTemplate();
    }

    @Bean
    public MyRestTemplate template3() {
        return new MyRestTemplate();
    }

}
