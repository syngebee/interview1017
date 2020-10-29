package com.itheima.interview.juc.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//资源类
class Ticket{
    private  int number = 2000;
    private  Lock lock = new ReentrantLock();

    //对外暴露的操作方法
    public  void saleTicket(){
        lock.lock();
        try {
            if (number > 0){
                System.out.println(Thread.currentThread().getName()+"\t卖出第"+number--+" 张票\t还剩下: "+number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class SaleTicket {
    public static void main(String[] args) {
        //创建资源类的实例
        Ticket ticket = new Ticket();
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                for (int k = 0; k < 2000; k++) {
                    ticket.saleTicket();
                }
            },(char)('A'+i)+"").start();
        }
    }



}
