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

@Component
public class Config3 {


    @Bean
    ClassA classb() {
        ClassA a = new ClassA();
        a.setA("testb");
        return a;
    }

    @Bean
    Object classc() {
        ClassA b = classb();
        return new Object();
    }

}
