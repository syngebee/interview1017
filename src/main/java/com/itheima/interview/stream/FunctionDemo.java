package com.itheima.interview.stream;

import java.util.function.Function;

public class FunctionDemo {
    public static void main(String[] args) {
        Function<String,Integer> function = String::length;
        System.out.println(function.apply("asdfsadf"));
    }
}
