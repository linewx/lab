package com.zz.playground.springboot;

import com.zz.playground.springboot.config.ClassA;
import com.zz.playground.springboot.config.ClassB;
import com.zz.playground.springboot.config.Config;
import com.zz.playground.springboot.config.JPAConfig;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

/**
 * Created by luganlin on 31/05/2018.
 */
@SpringBootApplication
//@EnableAutoConfiguration
@EnableAdminServer
public class Application {
    @Value("${server.port}")
    Integer port;
//
//    @Autowired
//    JPAConfig jpaConfig;

    @Autowired
    Config config;

    @Autowired
    ClassA a;
//
//    @Autowired
//    ClassB b;




    public static void main(String ...params) {

        //new SpringApplicationBuilder(Application.class).bannerMode(Banner.Mode.OFF).web(false).run(params);
        SpringApplication.run(Application.class, params);

    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            //System.out.println(a.getA());
            //System.out.println(b.getA());
            System.out.println(port);
//            System.out.println(jpaConfig.getServer());
            config.getServers().forEach(System.out::println);

            System.out.println("Let's inspect the beans provided by Spring Boot:");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        };
    }
}
