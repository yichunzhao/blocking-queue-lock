package com.ynz.multiplethreads;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor(staticName = "of")
public class Consumer<T> implements Runnable {
    private final MyQueue<T> tMyQueue;

    @Override
    public void run() {
        while (true) {
            T item = tMyQueue.dequeue();
            if (item.equals(Integer.MAX_VALUE)) break;
        }
    }
}
