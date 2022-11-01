package com.example.ankets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AnketsApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AnketsApplication.class, args);
    }

}
