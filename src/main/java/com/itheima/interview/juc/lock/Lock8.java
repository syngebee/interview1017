package com.itheima.interview.juc.lock;

import java.util.concurrent.TimeUnit;

class Phone{
    public synchronized void sendMail() {
        try{TimeUnit.SECONDS.sleep(4);}catch(InterruptedException e) {e.printStackTrace();}
        System.out.println("-----------sendMail");
    }
    public synchronized void sendMessage(){
        System.out.println("-----------sendMessage");
    }

    //       ↑普通             静态↓
    
    public static synchronized void sendMail2() {
        try{TimeUnit.SECONDS.sleep(4);}catch(InterruptedException e) {e.printStackTrace();}
        System.out.println("-----------sendMail2");
    }
    public static synchronized void sendMessage2(){
        System.out.println("-----------sendMessage2");
    }

    public void hello(){
        System.out.println("-----------hello");
    }

}

/**
 *  1. 标准访问, 主线程启动A线程, 主线程睡100毫秒, 先打印邮件还是打印短信?
 *  2. 邮件方法暂停4秒,短信方法不停, 先打印邮件还是短信?
 *  3. 新增一个普通方法hello,先打印邮件还是hello?
 *  4. 两部手机,俩资源类,phone1发邮件,phone2一个发短信. 先短信还是先邮件
 *  5. 两个静态同步方法, 同一部手机, 先打印邮件还是短信
 *  6. 两个静态同步方法, 两部手机, 先打印邮件还是短信
 *  7. 1个普通同步方法, 1个静态同步方法, 1部手机, 先打印邮件还是短信
 *  8. 1个普通同步方法, 1个静态同步方法, 2部手机, 先打印邮件还是短信
 */
public class Lock8 {
    public static void main(String[] args) throws InterruptedException {
        Phone phone1 = new Phone();
        Phone phone2 = new Phone();
        new Thread(()->{
            try {
//                phone1.sendMail();
                phone1.sendMail2();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"A线程").start();

        Thread.sleep(100);

        new Thread(()->{
            try {
//                phone1.sendMessage();
//                phone2.sendMessage();
//                phone1.hello();
                phone2.sendMessage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"B线程").start();


    }

    /* 我的答案:
        1. 先发邮件,后短信: 如果主线程不加Thread.sleep(100),那么不一定. B线程有机会先执行
        2. 先发邮件,后短信: synchronized加在普通方法上是对象锁, 同一对象一把锁. 这把锁A先拿到,B堵塞.
        3. 先hello,后邮件: Hello不需要锁
        4. 先短信,后邮件: 不是同一把对象锁,短信先执行完.
        5. 先邮件,后短信: synchronized加在静态方法上是类锁,同一类一把锁,这把锁A先拿到,B堵塞
        6. 先邮件,后短信: 理由同5.
        7. 先短信,后邮件: 一个是类锁, 一个是对象锁. 不管谁静态谁普通, 锁不一样 只看执行时间.
        8. 先短信,后邮件: 理由同7.
    */

}
