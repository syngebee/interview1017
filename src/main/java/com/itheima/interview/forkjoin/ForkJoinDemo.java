package com.itheima.interview.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * ForkJoinPool
 * ForkJoinTask
 * RecursiveTask
 */
public class ForkJoinDemo {
    public static void main(String[] args) {
        MyTask myTask = new MyTask(0, 50);

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        try {
            ForkJoinTask<Integer> forkJoinTask = forkJoinPool.submit(myTask);
            System.out.println(forkJoinTask.get());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            forkJoinPool.shutdown();
        }

    }
}

class MyTask extends RecursiveTask<Integer>{

    //临界值, 10以内不用fork/join
    public static final  Integer ADJUST_VALUE = 10;

    private int begin;
    private int end;
    private int result;

    public MyTask(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if ((end-begin) <= ADJUST_VALUE){
            System.out.println("执行运算: "+Thread.currentThread());
            for (int i = begin; i <= end; i++) {
                result += i;
            }
        }else{
            System.out.println("执行分离: "+Thread.currentThread());
            int middle = (end + begin)/2;
            MyTask myTask01 = new MyTask(begin, middle);
            MyTask myTask02 = new MyTask(middle + 1, end);
            myTask01.fork();
            myTask02.fork();//分
            result = myTask01.join() + myTask02.join();//合
        }
        return result;
    }
}