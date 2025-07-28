package com.demo.seek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication 
@EnableJpaRepositories(basePackages = "com.demo.seek.repository")
public class SeekApplication {
    public static void main(String[] args) {
        SpringApplication.run(SeekApplication.class, args);
    }
}