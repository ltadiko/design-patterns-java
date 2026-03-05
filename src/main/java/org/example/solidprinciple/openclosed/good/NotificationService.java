package org.example.solidprinciple.openclosed.good;

import java.util.List;

// STEP 3: The Service
public class NotificationService {
    private final List<NotificationHandler> handlers;

    public NotificationService(List<NotificationHandler> handlers) {
        this.handlers = handlers;
    }

    public void notify(String type, String message) {
        // TODO: Write the logic here to find the right handler and send the message.
        for (NotificationHandler handler : handlers) {
            if (handler.supports(type)) {
                handler.send(message);
                return;
            }
        }
        System.out.println("No handler found for type: " + type);
    }
}