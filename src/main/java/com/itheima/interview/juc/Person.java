package com.itheima.interview.juc;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Person {
    private Integer id;
    private String personName;

    public Person(String personName) {
        this.personName = personName;
    }
}
