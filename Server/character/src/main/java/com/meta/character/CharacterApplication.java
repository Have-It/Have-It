package com.meta.character;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class CharacterApplication {

    public static void main(String[] args) {
        SpringApplication.run(CharacterApplication.class, args);
    }

}
