package org.example.solidprinciple.singleresponsibilty.bad;

public class SmartThermostat {
    private int currentTemperature;

    public void setTemperature(int targetTemp) {
        this.currentTemperature = targetTemp;
        System.out.println("Temperature set to " + targetTemp);
    }

    public void connectToWiFi(String ssid, String password) {
        // Logic to establish network connection
        System.out.println("Connecting to " + ssid);
    }

    public void logTemperatureHistory(String filename) {
        // Logic to write temperature data to a local file
        System.out.println("Writing logs to " + filename);
    }
}
