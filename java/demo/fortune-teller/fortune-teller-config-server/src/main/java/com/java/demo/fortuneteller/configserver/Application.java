package com.java.demo.fortuneteller.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Created by luganlin on 13/07/2018.
 */

@SpringBootApplication
@EnableConfigServer

public class Application {
    public static void main(String... argv) {
        SpringApplication.run(Application.class, argv);
    }
}
