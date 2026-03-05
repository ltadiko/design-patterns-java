package org.example.solidprinciple.singleresponsibilty.good;

// 4. Reporting Class (Only changes if report formats change)
public class EmployeeReportGenerator {
    public String generate(Employee employee) {
        return "Employee Report: " + employee.getName() + " earns " + employee.getSalary();
    }
}
