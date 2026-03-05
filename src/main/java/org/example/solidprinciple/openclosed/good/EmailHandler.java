package org.example.solidprinciple.openclosed.good;

// STEP 2: Implement for Email
public class EmailHandler implements NotificationHandler {
    public boolean supports(String type) { return type.equals("EMAIL"); }
    public void send(String message) { System.out.println("Email: " + message); }
}
