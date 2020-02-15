package com.learn.model;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class User {
    @Id
    private Integer id;

    public User() {
    }

    private String name;

    public User(Integer id, String name, Integer dept, Integer salary) {
        this.id = id;
        this.name = name;
        this.dept = dept;
        this.salary = salary;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getDept() {
        return dept;
    }

    public Integer getSalary() {
        return salary;
    }

    private  Integer dept;
    private Integer salary;
}
