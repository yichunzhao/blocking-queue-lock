package com.ynz.multiplethreads;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor(staticName = "of")
public class Consumer<T> implements Runnable {
    private static int count = 0;

    private final MyQueue<T> tMyQueue;

    synchronized private static void increaseOne() {
        count++;
    }

    synchronized private static int count() {
        return count;
    }

    @Override
    public void run() {
        while (true) {
            T item = tMyQueue.dequeue();
            increaseOne();

            log.info("consumed " + item + " and total count: " + count());

            if (item.equals(Integer.MAX_VALUE)) {
                log.info("consumer receiving an ending ");
                break;
            }
        }
    }
}
