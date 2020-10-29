package com.itheima.interview.juc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class AirConditioner{
    private int a = 0;
    Lock lock = new ReentrantLock();
    Condition conAdd = lock.newCondition();
    Condition conDec = lock.newCondition();


    synchronized void addOne() throws InterruptedException {
        //判断
        while (a !=0){
            this.wait();
        }
        //干活
        a++;
        System.out.println(Thread.currentThread().getName()+"\t"+a);
        //通知
        this.notifyAll();

    }

    synchronized void decraseOne() throws InterruptedException {
        //判断
        while (a!=1){
            this.wait();
        }
        //干活
        a--;
        System.out.println(Thread.currentThread().getName()+"\t"+a);
        //通知
        this.notifyAll();
    }

    public void addLockOne() throws InterruptedException {
        lock.lock();
        try {
            while (a!=0){
                conAdd.await();
            }
            a++;
            System.out.println(Thread.currentThread().getName()+"\t"+a);
            conDec.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void decraseLockOne() throws InterruptedException {
        lock.lock();
        try {
            while (a!=1){
                conDec.await();
            }
            a--;
            System.out.println(Thread.currentThread().getName()+"\t"+a);
            conAdd.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class ThreadWaitNotifyDemo {
    public static void main(String[] args) {
        myLockDemo();
//        while (Thread.activeCount()>2){
//            Thread.yield();
//        }
//        System.out.println();
//        System.out.println("=================");
//        System.out.println();
//       synchronizedDemo();
    }

    public static void synchronizedDemo(){
        AirConditioner ac = new AirConditioner();
        new Thread(()->{
            for (int i = 0; i < 20; i++) {
                try {
                    ac.addOne();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"add2").start();

        new Thread(()->{
            for (int i = 0; i < 20; i++) {
                try {
                    ac.decraseOne();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"desc2").start();

        new Thread(()->{
            for (int i = 0; i < 20; i++) {
                try {
                    ac.addOne();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"add1").start();

        new Thread(()->{
            for (int i = 0; i < 20; i++) {
                try {
                    ac.decraseOne();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"desc1").start();
    }

    public static void myLockDemo(){
        AirConditioner ac = new AirConditioner();
        new Thread(()->{
            for (int i = 0; i < 20; i++) {
                try {
                    ac.addLockOne();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"add1").start();

        new Thread(()->{
            for (int i = 0; i < 20; i++) {
                try {
                    ac.decraseLockOne();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"desc1").start();

        new Thread(()->{
            for (int i = 0; i < 20; i++) {
                try {
                    ac.addLockOne();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"add2").start();

        new Thread(()->{
            for (int i = 0; i < 20; i++) {
                try {
                    ac.decraseLockOne();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"desc2").start();

    }
}
