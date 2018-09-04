package com.java.demo.fortuneteller.euraka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by luganlin on 13/07/2018.
 */

@SpringBootApplication
@EnableEurekaServer
public class Application {
    public static void main(String ...argv) {
        SpringApplication.run(Application.class, argv);
    }
}
