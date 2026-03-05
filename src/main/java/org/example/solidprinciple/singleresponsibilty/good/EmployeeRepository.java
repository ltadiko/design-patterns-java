package org.example.solidprinciple.singleresponsibilty.good;

// 3. Data Access Class (Only changes if the database tech changes)
public class EmployeeRepository {
    public void save(Employee employee) {
        System.out.println("Saving " + employee.getName() + " to the database...");
    }
}
