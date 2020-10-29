package com.itheima.interview.juc.atomic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicReference;

@Data
@AllArgsConstructor
@NoArgsConstructor
class User{
    String userName;
    int age;
}

/**
 * 原子引用
 * 原子包装
 */
public class AtomicReferenceDemo {
    public static void main(String[] args) {

        User zs = new User("zs", 22);
        User ls = new User("ls", 25);
        User ww = new User("ww", 25);

        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(zs);
        //主存中是zs, 期望zs, true. 发生修改
        System.out.println(atomicReference.compareAndSet(zs, ls));
        System.out.println("atomicReference = " + atomicReference.get());

        //主存现在是ls, 期望zs, false. 不发生修改
        System.out.println(atomicReference.compareAndSet(zs, ww));
        System.out.println("atomicReference = " + atomicReference.get());

    }
}
