package com.oscar.io;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Employee implements Serializable {
    String name;
    int age;
    LocalDateTime hireDate;

    public Employee(String name, int age, LocalDateTime hireDate) {
        this.name = name;
        this.age = age;
        this.hireDate = hireDate;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", hireDate=" + hireDate +
                '}';
    }
}
