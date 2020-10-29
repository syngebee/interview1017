package com.itheima.interview.juc;

public class TestTransferValue{
    public void changeValue1(int age){
        age=30;
    }

    public void changeValue2(Person person){
        person.setPersonName("xxx");
    }

    public void changeValue3(String str){
        str="xxx";
    }

    public static void main(String[] args) {
        TestTransferValue test = new TestTransferValue();
        int age = 20;
        test.changeValue1(age);
        System.out.println("age = " + age); //20

        Person person = new Person("abc");
        test.changeValue2(person);
        System.out.println("personName = " + person.getPersonName()); //xxx

        String str = "abc";
        test.changeValue3(str);
        System.out.println("str = " + str); //abc

    }
}