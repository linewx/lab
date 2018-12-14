package com.zz;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            final String uuid = UUID.randomUUID().toString().replace("-", "");
            Map bodyMap = new HashMap();
            bodyMap.put("originalTransKey","SDGr4234gG465356gGT44");
            bodyMap.put("originalGenTime","20180531124520");
            bodyMap.put("validPeriod","0");
            bodyMap.put("keyType","0");
            String privateKey = CreditApplication.privateKeyInvoke(
                    "https://apitest.tycredit.com/credit-front-http/credit/apply-for-private-key.json",
                    1,//测试标识testFlag,测试环境传1或5，生产环境传0
                    "mashangxiaowei001",
                    uuid,
                    "10151112140001000001542155961605",
                    bodyMap,
                    "645482c62f912c85"
            );



            Map bodyMap2 = new HashMap();
            bodyMap2.put("mobileNo", "18101947163");
            String result = CreditApplication.invoke("https://apitest.tycredit.com/credit-front-http/unified/wholeNetWorkStastus.json",
                    1,
                    "mashangxiaowei001",uuid, "10151112140001000001542155961601", bodyMap2, privateKey
                    );
            System.out.println(result);

        };
    }

}


