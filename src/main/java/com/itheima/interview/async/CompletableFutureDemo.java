package com.itheima.interview.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //runAsync
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + ": 模拟update数据库,无返回值");
        });
        completableFuture.get();

        //supplyAsync
        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + ": 模拟insert数据库,有返回值");
//            int age = 10/0;
            return 1024;
        });
        System.out.println(completableFuture2.whenComplete((t, u) -> {
            System.out.println("t = " + t);
            //抛出异常
            System.out.println("u = " + u);
        }).exceptionally(e -> {
            //可以根据异常判断返回什么值
            System.out.println("e = " + e.getMessage());
            return 444;
        }).get());
    }
}
