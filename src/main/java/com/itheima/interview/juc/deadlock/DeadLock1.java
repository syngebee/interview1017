package com.itheima.interview.juc.deadlock;

public class DeadLock1 {
    public static void main(String[] args) {


            new Thread(()->{
                for (int i = 1;i<=10;i++) {
                    synchronized ("A"){
                        System.out.println("线程一拿到A锁,等待B锁");
                        synchronized ("B") {
                        }
                    }
                }
            },"线程1").start();


        new Thread(()->{
            for (int i = 1;i<=10;i++) {
                synchronized ("B") {
                    System.out.println("线程二拿到B锁,等待A锁");
                    synchronized ("A") {

                    }
                }
            }
        },"线程2").start();
    }
}
