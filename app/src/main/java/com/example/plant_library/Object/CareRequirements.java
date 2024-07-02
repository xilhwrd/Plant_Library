package com.example.plant_library.Object;

public class CareRequirements {
    private String CareRate;
    private String CareStage;

    public CareRequirements() {
    }

    public CareRequirements(String careRate, String careStage) {
        CareRate = careRate;
        CareStage = careStage;
    }

    public String getCareRate() {
        return CareRate;
    }

    public void setCareRate(String careRate) {
        CareRate = careRate;
    }

    public String getCareStage() {
        return CareStage;
    }

    public void setCareStage(String careStage) {
        CareStage = careStage;
    }
}
