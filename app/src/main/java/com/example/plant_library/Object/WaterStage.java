package com.example.plant_library.Object;

public class WaterStage {
    private String Description;
    private int WateringInterval;

    public WaterStage() {
    }

    public WaterStage(String description, int wateringInterval) {
        Description = description;
        WateringInterval = wateringInterval;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getWateringInterval() {
        return WateringInterval;
    }

    public void setWateringInterval(int wateringInterval) {
        WateringInterval = wateringInterval;
    }
}
