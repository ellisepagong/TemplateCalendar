package com.github.ellisepagong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories("com.github.ellisepagong.database")
@EntityScan(basePackages = "com.github.ellisepagong.model")
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}