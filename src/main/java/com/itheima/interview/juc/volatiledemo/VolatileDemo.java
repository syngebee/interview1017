package com.itheima.interview.juc.volatiledemo;

import java.util.concurrent.TimeUnit;

class Student{
    //1.不加任何修饰
//    int age;
    //2. volatile修饰
    volatile int age;

    public void addTo60(){
        this.age = 60;
    }

    public void addPlusPlus(){
        age++;
    }
}

/**
 * 验证Volatile的可见性
 * 验证Volatile不保证原子性
 */
public class VolatileDemo {
    public static void main(String[] args) {

    }

    public static void NotAtomic(){
        Student student = new Student();
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                for (int i1 = 0; i1 < 1000; i1++) {
                    student.addPlusPlus();
                }
            }).start();
        }
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("student.age = " + student.age);
    }

    public static void seeOkByVolatile(){
        Student student = new Student();
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"come in");
            //暂停线程,让主线程读取到0
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //工作内存中修改值
            student.addTo60();
            System.out.println(Thread.currentThread().getName()+"student被更新到60");
        },"AAA").start();

        //1. 主线程仍旧是0,无限循环, 实现了可见性的反例.
        //2. 增加了volatile关键字, 保证了变量的可见性
        while (student.age==0){
        }
        System.out.println(Thread.currentThread().getName()+"\t");
    }
}
