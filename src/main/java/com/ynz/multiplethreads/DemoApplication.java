package com.ynz.multiplethreads;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class DemoApplication implements CommandLineRunner {
    private final MyQueue<Integer> integerMyQueue;
    private final ExecutorService service;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        IntStream.range(0, 100).forEach(i -> service.submit(Producer.of(integerMyQueue, i)));
        IntStream.range(0, 5).forEach(i -> service.submit(Consumer.of(integerMyQueue)));

        shutDown();

        System.out.println("total consumed: " + ((Statistics) integerMyQueue).getTotalConsumed());
        System.out.println("total produced: " + ((Statistics) integerMyQueue).getTotalProduced());
    }

    @SneakyThrows
    private void shutDown() {
        service.shutdown();
        service.awaitTermination(1000, TimeUnit.MILLISECONDS);
        service.isShutdown();
        log.info("service shut down? " + service.isShutdown());
    }

}
