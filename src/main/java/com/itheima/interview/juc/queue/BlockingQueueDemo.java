package com.itheima.interview.juc.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        //创建一个数据结构的有界阻塞队列
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        //System.out.println(blockingQueue.element()); // NoSuchElementException


//------ 组1: 抛出异常组
        /*System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));*/
        //System.out.println(blockingQueue.add("d")); // Queue full

        /*System.out.println(blockingQueue.remove()); // a
        System.out.println(blockingQueue.remove()); // b
        System.out.println(blockingQueue.remove()); // c */
        //System.out.println(blockingQueue.remove()); // no such element exception
        //System.out.println(blockingQueue.element()); // 娄一眼下一个获取的元素

//------ 组2: 特殊返回值组 布尔
        /*System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));*/
        //System.out.println(blockingQueue.offer("d")); // false

        /*System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());*/
        //System.out.println(blockingQueue.poll()); // null


        //结果    执行,   执行,   执行,   阻塞
        System.out.println("a放入中");
        blockingQueue.put("a"); // 执行
        System.out.println("b放入中");
        blockingQueue.put("b"); // 执行
        System.out.println("c放入中");
        blockingQueue.put("c"); // 执行

        System.out.println(blockingQueue.take());//拿取

        System.out.println("d放入中");
        blockingQueue.put("d"); // 阻塞


    }
}
