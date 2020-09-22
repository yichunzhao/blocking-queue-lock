package com.ynz.multiplethreads;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor(staticName = "of")
public class Producer<T> implements Runnable {
    private final MyQueue<T> tMyQueue;
    private final T product;

    @Override
    public void run() {
        tMyQueue.enqueue(product);
    }
}
