package com.itheima.interview.juc.deadlock;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//口诀: 线程操纵资源类

//资源类
class HoldLockThread implements Runnable{
    private final String lock1;
    private final String lock2;

    public HoldLockThread(String lock1, String lock2) {
        this.lock1 = lock1;
        this.lock2 = lock2;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            synchronized (lock1){
                System.out.println(Thread.currentThread().getName()+"拿到锁"+lock1+",等待锁"+lock2);
                synchronized (lock2){
                }
            }
        }
    }
}

//线程(池)
public class DeadLock2 {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 5, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        threadPoolExecutor.execute(new HoldLockThread("A","B"));
        threadPoolExecutor.execute(new HoldLockThread("B","A"));
    }
}
