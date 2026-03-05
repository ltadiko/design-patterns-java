package org.example.solidprinciple.singleresponsibilty.good;

public class SmartThermostat {
    private int currentTemperature;

    public void setTemperature(int targetTemp) {
        this.currentTemperature = targetTemp;
        System.out.println("Temperature set to " + targetTemp);
    }

}
