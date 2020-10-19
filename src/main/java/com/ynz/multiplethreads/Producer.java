package com.ynz.multiplethreads;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor(staticName = "of")
public class Producer<T> implements Runnable {
    private static AtomicInteger count = new AtomicInteger();

    private final MyQueue<T> tMyQueue;
    private final T product;

    @Override
    public void run() {
        tMyQueue.enqueue(product);
        log.info("produce: " + product + " total produced: " + count.addAndGet(1));

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            log.error("producer exception: ", e);
        }
    }
}
