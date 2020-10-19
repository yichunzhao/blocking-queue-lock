package com.ynz.multiplethreads;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Component
public class MyBlockingQueue<T> implements MyQueue<T> {
    @Value("${queue.size}")
    private int maxSize;

    private Queue<T> myQueue = new LinkedList<>();

    private Lock lock = new ReentrantLock();

    private Condition isFull = lock.newCondition();
    private Condition isEmpty = lock.newCondition();

    @SneakyThrows
    @Override
    public void enqueue(T item) {
        lock.lock();

        try {
            if (myQueue.size() == maxSize) {
                isFull.await();//block threads to enq.
            }

            myQueue.offer(item);
            isEmpty.signalAll();//notify waiting threads to produce more items in the Q.

        } finally {
            lock.unlock();
        }
    }

    @SneakyThrows
    @Override
    public T dequeue() {
        lock.lock();
        try {
            while (myQueue.size() == 0) isEmpty.await();//block all threads to read.
            T iterm = myQueue.poll();//remove a item
            isFull.signalAll();
            return iterm;
        } finally {
            lock.unlock();
        }
    }

}
