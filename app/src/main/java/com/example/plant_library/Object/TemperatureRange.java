package com.example.plant_library.Object;

public class TemperatureRange {
    private String TemperatureRate;
    private String TemperatureStage;

    public TemperatureRange() {
    }

    public TemperatureRange(String temperatureRate, String temperatureStage) {
        TemperatureRate = temperatureRate;
        TemperatureStage = temperatureStage;
    }

    public String getTemperatureRate() {
        return TemperatureRate;
    }

    public void setTemperatureRate(String temperatureRate) {
        TemperatureRate = temperatureRate;
    }

    public String getTemperatureStage() {
        return TemperatureStage;
    }

    public void setTemperatureStage(String temperatureStage) {
        TemperatureStage = temperatureStage;
    }
}
