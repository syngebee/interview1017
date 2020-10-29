package com.itheima.interview.juc.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Text{
    private int num = 1;
    private char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    private Lock lock = new ReentrantLock();
    Condition conditionNum = lock.newCondition();
    Condition conditionChar = lock.newCondition();

    public void AddNum(){
        lock.lock();
        try {
            while( (num % 3) != 1 && (num % 3) !=2){
                conditionNum.await();
            }

            System.out.println(Thread.currentThread().getName()+" \t num = " + num);
            num++;
            conditionChar.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void AddChar(){
        lock.lock();
        try {
            while((num % 3) != 0){
                conditionChar.await();
            }
            System.out.println(Thread.currentThread().getName()+" \t cha = " + chars[num % 52-1]);
            num++;
            conditionNum.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

}

public class ThreadTest {
    public static void main(String[] args) {
        Text text = new Text();

        new Thread(()->{
            for (int i = 0; i < 20; i++) {
                text.AddNum();
            }
        },"Num").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                text.AddChar();
            }
        },"Char").start();


    }
}
