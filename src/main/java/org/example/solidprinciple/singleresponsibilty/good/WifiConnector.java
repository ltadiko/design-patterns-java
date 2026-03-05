package org.example.solidprinciple.singleresponsibilty.good;

public class WifiConnector {
    public void connectToWiFi(String ssid, String password) {
        // Logic to establish network connection
        System.out.println("Connecting to " + ssid);
    }
}
