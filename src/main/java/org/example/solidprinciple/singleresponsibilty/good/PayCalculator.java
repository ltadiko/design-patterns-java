package org.example.solidprinciple.singleresponsibilty.good;

// 2. Business Logic Class (Only changes if tax rules change)
public class PayCalculator {
    public double calculatePay(Employee employee) {
        return employee.getSalary() - (employee.getSalary() * 0.20);
    }
}
