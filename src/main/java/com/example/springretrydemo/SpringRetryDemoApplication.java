package com.example.springretrydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class SpringRetryDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRetryDemoApplication.class, args);
    }

}
