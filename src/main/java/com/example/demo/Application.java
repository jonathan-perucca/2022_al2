package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class Application {

    public static void main(String[] args) {
        var context = SpringApplication.run(Application.class, args);

        var userConfig = context.getBean(UserConfig.class);

        System.out.println("[Max users] : " + userConfig.getMaxUsers());
    }

}
