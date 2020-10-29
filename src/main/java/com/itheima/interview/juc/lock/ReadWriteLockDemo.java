package com.itheima.interview.juc.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyCache{
    private volatile Map<String,Object> map = new HashMap<>();
    //定义一个可重入的读写锁
    private ReadWriteLock readWriteLock= new ReentrantReadWriteLock();

    public void put(String key, Object value){
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 开始写入"+key);
            try{TimeUnit.MILLISECONDS.sleep(300);}catch(InterruptedException e) {e.printStackTrace();}

            //逻辑代码
            map.put(key,value);

            System.out.println(Thread.currentThread().getName()+"\t 写入完成"+key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void get(String key){
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 开始读取");

            //逻辑代码
            Object res = map.get(key);

            System.out.println(Thread.currentThread().getName()+"\t 读取完成"+res);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        for (int i = 1; i <= 5; i++) {
            final int tempInt =i;


            new Thread(()->{

                //逻辑代码
                myCache.put(tempInt+"",tempInt);

            },String.valueOf(i)).start();

            new Thread(()->{

                //逻辑代码
                myCache.get(tempInt+"");

            },String.valueOf(i)).start();
        }
    }
}
