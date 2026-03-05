package org.example.solidprinciple.singleresponsibilty.good;

// 1. Data/Domain Class (Only changes if Employee attributes change)
public class Employee {
    private String name;
    private double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() { return name; }
    public double getSalary() { return salary; }
}
