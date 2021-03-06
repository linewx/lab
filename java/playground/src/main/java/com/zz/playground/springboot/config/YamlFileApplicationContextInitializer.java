package com.zz.playground.springboot.config;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

public class YamlFileApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
    try {
        Resource resource = applicationContext.getResource("classpath:config.yml");
        YamlPropertySourceLoader sourceLoader = new YamlPropertySourceLoader();
        List<PropertySource<?>> result = sourceLoader.load("yamlTestProperties", resource);
        PropertySource<?> yamlTestProperties = result.get(0);
        applicationContext.getEnvironment().getPropertySources().addFirst(yamlTestProperties);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
  }
}