package org.example.solidprinciple.singleresponsibilty.bad;

public class Employee {
    private String name;
    private double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    // Responsibility 1: Core domain logic
    public double calculatePay() {
        return salary - (salary * 0.20); // deducting taxes
    }

    // Responsibility 2: Database operations
    public void saveToDatabase() {
        // JDBC code to connect to DB and execute INSERT statement
        System.out.println("Saving " + name + " to the database...");
    }

    // Responsibility 3: Formatting/Reporting
    public String generateReport() {
        return "Employee Report: " + name + " earns " + salary;
    }
}
