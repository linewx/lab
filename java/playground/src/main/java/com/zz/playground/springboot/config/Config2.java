package com.zz.playground.springboot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luganlin on 20/07/2018.
 */

@Configuration
public class Config2 {


    @Bean
    ClassB class1() {
        ClassB a = new ClassB();
        a.setA("testb");
        return a;
    }

    @Bean
    Object class2() {
        ClassB b = class1();
        return new Object();
    }

}