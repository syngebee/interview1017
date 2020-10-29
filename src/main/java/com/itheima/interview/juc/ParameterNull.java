package com.itheima.interview.juc;

import java.util.ArrayList;
import java.util.List;

public class ParameterNull {
    public static void main (String[] args){
        List<String> a = null;
        test(a);
        System.out.println(a.size());
    }
    public static void test(List<String> a){
        //形参修改引用,和实参无关
        a= new ArrayList<>();
        a.add("abc");
    }

}