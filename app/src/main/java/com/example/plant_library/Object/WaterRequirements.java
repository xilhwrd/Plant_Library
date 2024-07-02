package com.example.plant_library.Object;

public class WaterRequirements {
    private String WaterRate;
    private String WaterStage1;
    private String WaterStage2;
    private String WaterStage3;
    private String WaterStage4;

    public WaterRequirements() {
    }


    public WaterRequirements(String waterRate, String waterStage1, String waterStage2, String waterStage3, String waterStage4) {
        WaterRate = waterRate;
        WaterStage1 = waterStage1;
        WaterStage2 = waterStage2;
        WaterStage3 = waterStage3;
        WaterStage4 = waterStage4;
    }

    public String getWaterRate() {
        return WaterRate;
    }

    public void setWaterRate(String waterRate) {
        WaterRate = waterRate;
    }

    public String getWaterStage1() {
        return WaterStage1;
    }

    public void setWaterStage1(String waterStage1) {
        WaterStage1 = waterStage1;
    }

    public String getWaterStage2() {
        return WaterStage2;
    }

    public void setWaterStage2(String waterStage2) {
        WaterStage2 = waterStage2;
    }

    public String getWaterStage3() {
        return WaterStage3;
    }

    public void setWaterStage3(String waterStage3) {
        WaterStage3 = waterStage3;
    }

    public String getWaterStage4() {
        return WaterStage4;
    }

    public void setWaterStage4(String waterStage4) {
        WaterStage4 = waterStage4;
    }
}
