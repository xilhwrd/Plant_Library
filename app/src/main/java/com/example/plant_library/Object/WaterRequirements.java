package com.example.plant_library.Object;

public class WaterRequirements {
    private String WaterRate;
    private WaterStage WaterStage1;
    private WaterStage WaterStage2;
    private WaterStage WaterStage3;
    private WaterStage WaterStage4;

    public WaterRequirements() {
    }

    public WaterRequirements(String waterRate, WaterStage waterStage1, WaterStage waterStage2, WaterStage waterStage3, WaterStage waterStage4) {
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

    public WaterStage getWaterStage1() {
        return WaterStage1;
    }

    public void setWaterStage1(WaterStage waterStage1) {
        WaterStage1 = waterStage1;
    }

    public WaterStage getWaterStage2() {
        return WaterStage2;
    }

    public void setWaterStage2(WaterStage waterStage2) {
        WaterStage2 = waterStage2;
    }

    public WaterStage getWaterStage3() {
        return WaterStage3;
    }

    public void setWaterStage3(WaterStage waterStage3) {
        WaterStage3 = waterStage3;
    }

    public WaterStage getWaterStage4() {
        return WaterStage4;
    }

    public void setWaterStage4(WaterStage waterStage4) {
        WaterStage4 = waterStage4;
    }
}
