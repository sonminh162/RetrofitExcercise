package com.lifetime.retrofitexercise.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Employee implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("employee_name")
    private String name;

    @SerializedName("employee_salary")
    private String salary;

    @SerializedName("employee_age")
    private String age;

    public Employee(String name, String salary, String age){
        this.name = name;
        this.salary = salary;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
