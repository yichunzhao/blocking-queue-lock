package com.ynz.multiplethreads;


import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Component
public class MyBlockingQueue<T> implements MyQueue<T>, Statistics {
    private Deque<T> myQueue = new ArrayDeque<>();

    private Lock lock = new ReentrantLock();
    private Condition isFull = lock.newCondition();
    private Condition isEmpty = lock.newCondition();
    private Condition isNotFull = lock.newCondition();

    @Getter
    private int totalProduced;
    @Getter
    private int totalConsumed;

    @SneakyThrows
    @Override
    public T dequeue() {
        lock.lock();

        try {
            if (myQueue.isEmpty()) isEmpty.wait();

            T polled = myQueue.poll();
            totalConsumed++;
            log.info("DeQ: " + polled.toString());
            isNotFull.signal();
            return polled;
        } finally {
            lock.unlock();
        }
    }

    @SneakyThrows
    @Override
    public void enqueue(T t) {
        lock.lock();

        try {
            //if (myQueue.size() == 20) isFull.wait();

            if (myQueue.offer(t)) {
                totalProduced++;
                log.info("EnQ:" + t.toString());
                isNotFull.signal();
            }
        } finally {
            lock.unlock();
        }
    }
}
