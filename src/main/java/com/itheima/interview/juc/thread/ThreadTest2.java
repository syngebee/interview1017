package com.itheima.interview.juc.thread;

import sun.awt.SunToolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Num{
    private Lock lock = new ReentrantLock();
    //执行逻辑的标志位
    private int num = 1;
    //用作线程精确唤醒的condition集合
    private List<Condition> conditionList = new ArrayList<>();

    //优化
    /*Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();*/

    //初始化3套逻辑,加入conditionList
    public Num() {
        for (int i = 0; i < 3; i++) {
            conditionList.add(lock.newCondition());
        }
    }

    public void printNum(){
        lock.lock();
        try {
            String threadName = Thread.currentThread().getName();
            char c = threadName.charAt(threadName.length()-1);
            //判断线程名称是否符合约定
            if (!Character.isDigit(c)) throw new SunToolkit.IllegalThreadException("线程名不正确,末尾数字约定");
            //当前线程应该走的哪套逻辑
            int threadNum = c - 48;

            //判断
            while(num != threadNum){
                //优化
                conditionList.get(threadNum-1).await();
                System.out.println("因为程序计数器,con"+threadNum+"从上次沉睡点苏醒 优先执行");
                /*switch (threadNum){
                    case 1: condition1.await();break;
                    case 2: condition2.await();break;
                    case 3: condition3.await();break;
                }*/
            }
            //干活,   逻辑1: 打印5次,    逻辑2: 打印10次,     逻辑3: 打印15次
            for (int i = 0; i < 5 * threadNum; i++) {
                System.out.println(Thread.currentThread().getName()+": \t"+(i+1));
            }
            System.out.println("-------------------------------------------");
            //通知
            num = (threadNum==3 ? 1 :threadNum + 1);
            System.out.println("尝试唤醒con"+num+"线程");
            //优化--尝试唤醒
            conditionList.get(threadNum==3 ? 0 : threadNum).signal();
            /*switch (threadNum){
                case 1: condition2.signal();break;
                case 2: condition3.signal();break;
                case 3: condition1.signal();break;
            }*/
       } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class ThreadTest2 {
    public static void main(String[] args) {
        Num num = new Num();
        //开启三个不一样名字的线程
        for (int i = 1; i <= 3; i++) {
            //每个线程循环调10次
            new Thread(()->{
                for (int i1 = 0; i1 < 10; i1++) {
                    num.printNum();
                }
           },"con"+i).start();
        }
    }
}
