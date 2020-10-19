package com.ynz.multiplethreads;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor(staticName = "of")
public class Producer<T> implements Runnable {
    private static int count;

    private final MyQueue<T> tMyQueue;
    private final T product;

    synchronized private void increaseOne() {
        count++;
    }

    synchronized private int count() {
        return count;
    }

    @Override
    public void run() {
        tMyQueue.enqueue(product);
        increaseOne();
        log.info("produce: " + product + " total produced: " + count());

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            log.error("producer exception: ", e);
        }
    }
}
