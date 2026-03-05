package org.example.solidprinciple.openclosed.good;

// STEP 1: Define the interface
public interface NotificationHandler {
    boolean supports(String type);

    void send(String message);
}
