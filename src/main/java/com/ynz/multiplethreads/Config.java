package com.ynz.multiplethreads;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class Config {

    @Bean
    public ExecutorService service() {
        return Executors.newFixedThreadPool(10);
    }

}
