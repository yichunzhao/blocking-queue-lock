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

    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    @SneakyThrows
    @Override
    public void enqueue(T item) {
        lock.lock();

        try {
            if (myQueue.size() == maxSize) {
                notFull.await();//block threads to enq.
            }
            myQueue.offer(item);
            notEmpty.signalAll();//notify waiting threads to produce more items in the Q.
        } finally {
            lock.unlock();
        }
    }

    @SneakyThrows
    @Override
    public T dequeue() {
        lock.lock();

        try {
            if (myQueue.isEmpty()) notEmpty.await();//block all threads to read.
            T iterm = myQueue.poll();//remove a item
            notFull.signalAll();
            return iterm;
        } finally {
            lock.unlock();
        }
    }

}
