package com.itheima.interview.juc.countclass;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3); //模拟有3个空车位

        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                try {
                    //占用,使信号量-1
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+" \t占用车位");
                    //停一会儿,为了看出效果 是不是每次只能同时有3人
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    //释放, 使信号量+1
                    semaphore.release();
                    System.out.println(Thread.currentThread().getName()+" \t离开车位");
                }
            },String.valueOf(i)).start();
        }
    }
}
