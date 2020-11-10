# Blocking-queue-lock

Blocking queue using explicit lock.

Threads are blocked when the queue is empty or reaching the max size;  if putting producers and consumers in one thread pool, it shows that all threads are blocked soon, there is no thread to wake up the rest threads. The queue operations are therefore stuck. 

It may have another solution, using tryLock together with a thread pool. 

