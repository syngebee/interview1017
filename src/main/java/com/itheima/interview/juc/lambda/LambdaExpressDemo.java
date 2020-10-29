package com.itheima.interview.juc.lambda;

@FunctionalInterface
interface Foo{
    void sayHello(int x,int y);

    default void sayHello2(){
        System.out.println("Hello2");
    }

    default void sayHello3(){
        System.out.println("Hello3");
    }
}

public class LambdaExpressDemo {
    public static void main(String[] args) {
        Foo hello = (x,y)-> System.out.println("Hello,"+x+"+"+y);
    }

}
