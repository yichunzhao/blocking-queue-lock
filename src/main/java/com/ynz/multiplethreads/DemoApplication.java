package com.ynz.multiplethreads;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.stream.IntStream;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class DemoApplication implements CommandLineRunner {
    private final static int PRODUCER_NUM = 50;
    private final static int CONSUMER_NUM = 5;

    private final MyQueue<Integer> integerMyQueue;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        IntStream.rangeClosed(0, PRODUCER_NUM).forEach(i -> {
            //if i = max of product num, putting ending capsules.
            if (i == PRODUCER_NUM) {
                IntStream.rangeClosed(1, CONSUMER_NUM).forEach(i1 -> new Thread(Producer.of(integerMyQueue, Integer.MAX_VALUE)).start());
            } else new Thread(Producer.of(integerMyQueue, i)).start();
        });

        IntStream.range(0, CONSUMER_NUM).forEach(i -> {
            Thread th = new Thread(Consumer.of(integerMyQueue));
            th.start();
        });

    }

}
