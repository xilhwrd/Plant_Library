package com.example.plant_library.Object;

public class LightRequirements {
    private String LightRate;
    private String LightStage1;
    private String LightStage2;
    private String LightStage3;
    private String LightStage4;

    public LightRequirements() {
    }

    public LightRequirements(String lightRate, String lightStage1, String lightStage2, String lightStage3, String lightStage4) {
        LightRate = lightRate;
        LightStage1 = lightStage1;
        LightStage2 = lightStage2;
        LightStage3 = lightStage3;
        LightStage4 = lightStage4;
    }

    public String getLightRate() {
        return LightRate;
    }

    public void setLightRate(String lightRate) {
        LightRate = lightRate;
    }

    public String getLightStage1() {
        return LightStage1;
    }

    public void setLightStage1(String lightStage1) {
        LightStage1 = lightStage1;
    }

    public String getLightStage2() {
        return LightStage2;
    }

    public void setLightStage2(String lightStage2) {
        LightStage2 = lightStage2;
    }

    public String getLightStage3() {
        return LightStage3;
    }

    public void setLightStage3(String lightStage3) {
        LightStage3 = lightStage3;
    }

    public String getLightStage4() {
        return LightStage4;
    }

    public void setLightStage4(String lightStage4) {
        LightStage4 = lightStage4;
    }
}
