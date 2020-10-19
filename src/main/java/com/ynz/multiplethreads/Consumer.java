package com.ynz.multiplethreads;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor(staticName = "of")
public class Consumer<T> implements Runnable {
    private static AtomicInteger count = new AtomicInteger();

    private final MyQueue<T> tMyQueue;

    @Override
    public void run() {
        while (true) {
            T item = tMyQueue.dequeue();

            log.info("consumed " + item + " and total count: " + count.addAndGet(1));

            if (item.equals(Integer.MAX_VALUE)) {
                log.info("consumer receiving an ending ");
                break;
            }
        }
    }
}
