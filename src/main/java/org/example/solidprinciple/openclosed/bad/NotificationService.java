package org.example.solidprinciple.openclosed.bad;

/// Here is that "Bad" NotificationService again. It is Closed for Extension (you have to change the if/else to add a new type)
/// and Open for Modification (you keep touching the same file).
public class NotificationService {
    public void sendNotification(String type, String message) {
        if (type.equals("EMAIL")) {
            System.out.println("Sending Email: " + message);
        } else if (type.equals("SMS")) {
            System.out.println("Sending SMS: " + message);
        }
    }
}
