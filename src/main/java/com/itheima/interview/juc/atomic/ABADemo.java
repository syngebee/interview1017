package com.itheima.interview.juc.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABADemo {

    //原子引用变量, 用于演示ABA问题的产生
    private static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    //原子引用版本控制, 用于演示ABA问题的解决:
    //第一个参数为原子引用,第二个值为版本
    private static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100,1);


    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(2);
        System.out.println("========================== ABA问题的演示 ============================");
        new Thread(()->{

            //停100ms 先让t2读取原始副本100(version=1)
            try{TimeUnit.MILLISECONDS.sleep(100);}catch(InterruptedException e) {e.printStackTrace();}

            //模仿CAS先获取 -------- A
            Integer value = atomicReference.get();
            System.out.println("t1拿到原始数据"+value+"\tversion1");

            //获取的值作为期望值CAS操作 -------- B
            Boolean flag = atomicReference.compareAndSet(value, ++value);
            System.out.println("线程"+Thread.currentThread().getName()+":"+flag+"\t当前值为"+atomicReference.get()+"\tversion2");

            //模仿CAS先获取
            value=atomicReference.get();
            //获取的值作为期望值CAS操作 -------- A
            flag=atomicReference.compareAndSet(value,--value);
            System.out.println("线程"+Thread.currentThread().getName()+":"+flag+"\t当前值为"+atomicReference.get()+"\tversion3");

            countDownLatch.countDown();
        },"t1").start();

        new Thread(()->{
            //模仿CAS先获取 -------- A
            Integer value = atomicReference.get();
            System.out.println("t2拿到原始数据"+value+"\tversion1 ########### 注意此处");

            //停200ms让t1做完ABA操作
            try{TimeUnit.MILLISECONDS.sleep(200);}catch(InterruptedException e) {e.printStackTrace();}

            //获取的值作为期望值CAS操作 -------- A
            Boolean flag = atomicReference.compareAndSet(value, value * 2);

            //重新获取值并打印
            System.out.println("线程"
                    + Thread.currentThread().getName()
                    + ":" + flag
                    + "\t" + "当前值为"+atomicReference.get()
                    + "\t" + "version3"
                    + "\t" + "不该发生修改,虽然数值一样,但数据被修改过");
            countDownLatch.countDown();
        },"t2").start();

        countDownLatch.await();
        System.out.println();
        System.out.println("========================== ABA问题的解决 ============================");

        new Thread(()->{
            //模仿CAS先获取 版本号和值 -------- A
            Integer reference = atomicStampedReference.getReference();
            int stampVersion = atomicStampedReference.getStamp();

            System.out.println("t1拿到原始数据"+reference
                    + "\t" + "version"+stampVersion);

            //停100ms 先让t2读取原始副本100(version=1)
            try{TimeUnit.MILLISECONDS.sleep(100);}catch(InterruptedException e) {e.printStackTrace();}

            //获取的 版本号和值 作为期望值CAS操作 -------- B
            Boolean flag = atomicStampedReference.compareAndSet(reference,++reference,stampVersion,++stampVersion);

            //重新获取 版本号和值 并打印
            System.out.println("线程"+Thread.currentThread().getName()
                    + ":" + flag + " \t" + "当前值为" + atomicStampedReference.getReference()
                    + "\t" + "version" + atomicStampedReference.getStamp());

            //模仿CAS先获取 版本号和值 -------- A
            reference=atomicStampedReference.getReference();
            stampVersion=atomicStampedReference.getStamp();

            //获取的 版本号和值 作为期望值CAS操作
            flag = atomicStampedReference.compareAndSet(reference, --reference, stampVersion, ++stampVersion);
            //重新获取值并打印
            System.out.println("线程" + Thread.currentThread().getName()
                    + ":"+flag+ " \t" + "当前值为" + atomicStampedReference.getReference()
                    + "\t" + "version" + atomicStampedReference.getStamp());

        },"t1").start();

        new Thread(()->{
            //模仿CAS先获取 版本号和值 -------- A
            Integer reference = atomicStampedReference.getReference();
            int stampVersion = atomicStampedReference.getStamp();

            System.out.println("t2拿到原始数据"
                    + reference
                    + "\tversion"+stampVersion+" ########### 注意此处");

            //停200ms让t1做完ABA操作
            try{TimeUnit.MILLISECONDS.sleep(200);}catch(InterruptedException e) {e.printStackTrace();}

            //获取的 版本号和值 作为期望值CAS操作 -------- A
            Boolean flag = atomicStampedReference.compareAndSet(reference, reference * 2, stampVersion, ++stampVersion);
            //重新获取值并打印
            System.out.println("线程"+Thread.currentThread().getName()
                    + ":" + flag+ " \t" + "当前值为"+atomicStampedReference.getReference()
                    + "\t" + "version" + atomicStampedReference.getStamp()
                    + "\t"+"因版本号不一致未完成修改, ABA问题解决");
        },"t2").start();
    }
}
