package com.itheima.interview.juc.threadpool;

import java.util.concurrent.*;

public class MyThreadPoolDemo {
    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());
        //手写
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                2,
                5,
                2L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        try {
            for (int i = 0; i < 12; i++) {
//                try{TimeUnit.SECONDS.sleep(1);}catch(InterruptedException e) {e.printStackTrace();}
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName());
                });
                //try{TimeUnit.MILLISECONDS.sleep(400);}catch(InterruptedException e) {e.printStackTrace();}
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }

    }


    //使用Executors的三大方法创建线程池
        //不准用, newFixedThreadPool 和 SingleThread, 使用的LinkedBlockingQueue 队列长度为Integer.MAX_VALUE,会堆积大量的请求, OOM
        //        newCachedThreadPool 和 newScheduledThreadPool, 最大线程数为Integer.MAX_VALUE,会创建大量的线程, OOM
    public static void initPool(){
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
//        ExecutorService threadPool2 = Executors.newSingleThreadExecutor();
        ExecutorService threadPool3 = Executors.newCachedThreadPool();
        /*try {
            for (int i = 0; i < 10; i++) {
                try{TimeUnit.SECONDS.sleep(1);}catch(InterruptedException e) {e.printStackTrace();}
                threadPool3.execute(()->{
                    System.out.println(Thread.currentThread().getName());
                    System.out.println(Thread.currentThread().getName());
                });
                //try{TimeUnit.MILLISECONDS.sleep(400);}catch(InterruptedException e) {e.printStackTrace();}
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool3.shutdown();
        }*/
    }
}
