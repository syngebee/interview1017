package com.itheima.interview.juc.thread;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

class MyThread implements Runnable{
    @Override
    public void run() {

    }
}

class MyThread2 implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        System.out.println("****come in");
        try{TimeUnit.SECONDS.sleep(4);}catch(InterruptedException e) {e.printStackTrace();}
        return 1024;
    }
}

public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread2());
        new Thread(futureTask,"A").start();
        new Thread(futureTask,"B").start();
        System.out.println(futureTask.get());
    }
}
