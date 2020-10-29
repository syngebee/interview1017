package com.itheima.interview.stream;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Stream02 {
    //1. 第一个队伍只要名字3个字的成员
    //2. 第一个队伍筛选后只要前三人
    //3. 第二个队伍只要姓张的人
    //4. 第二个队伍不要前两个人
    //5. 两队伍合并
    //6. 根据姓名创建Person对象
    //7. 打印整个队伍的信息
    public static void main(String[] args) {
        ArrayList<String> arr1 = new ArrayList<>();
        arr1.add("迪丽热巴");
        arr1.add("宋远桥");
        arr1.add("苏星河");
        arr1.add("石破天");
        arr1.add("石中玉");
        arr1.add("老子");
        arr1.add("庄子");
        arr1.add("洪七公");
        //1. 第一个队伍只要名字3个字的成员
        //2. 第一个队伍筛选后只要前三人
        Stream<String> stream1 = arr1.stream().filter(name -> name.length() == 3).limit(3);

        ArrayList<String> arr2 = new ArrayList<>();
        arr2.add("古力娜扎");
        arr2.add("张无忌");
        arr2.add("赵丽颖");
        arr2.add("张三丰");
        arr2.add("尼古拉斯赵四");
        arr2.add("张天爱");
        arr2.add("张二狗");
        //3. 第二个队伍只要姓张的人
        //4. 第二个队伍不要前两个人
        Stream<String> stream2 = arr2.stream().filter(name -> name.startsWith("张")).skip(2);
        //5. 两队伍合并
        //6. 根据姓名创建Person对象
        //7. 打印整个队伍的信息
        Stream.concat(stream1, stream2).map(StreamPerson::new).forEach(System.out::println);
    }
}
