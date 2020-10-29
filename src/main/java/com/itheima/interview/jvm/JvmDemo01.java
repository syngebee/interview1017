package com.itheima.interview.jvm;

import java.util.Random;

public class JvmDemo01 {
    public static void main(String[] args) {
        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();

        //查看内存状况
        System.out.println("-Xmx: maxMemory = " + (maxMemory/(double)1024/1024)+"MB");
        System.out.println("-Xms: totalMemory = " + (totalMemory/(double)1024/1024)+"MB");
        //查看可用线程数
        System.out.println(Runtime.getRuntime().availableProcessors());

        //堆内存溢出方法1
        String str = "aabbcc";
        while (true){
            str+= str + new Random().nextInt(888888888)+new Random().nextInt(999999999);
        }
        //堆内存溢出方法2
//        byte[] bytes = new byte[40 * 1024 *1024];

    }
}
