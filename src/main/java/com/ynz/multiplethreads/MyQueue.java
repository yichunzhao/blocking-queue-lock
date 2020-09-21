package com.ynz.multiplethreads;

public interface MyQueue<T> {
    T dequeue();

    void enqueue(T t);
}
