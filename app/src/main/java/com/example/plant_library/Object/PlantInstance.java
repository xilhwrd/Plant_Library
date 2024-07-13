package com.example.plant_library.Object;

public class PlantInstance {
    private int PlantID;
    private String DatePlanted;
    private String StageName;

    public PlantInstance() {
        // Default constructor required for calls to DataSnapshot.getValue(PlantInstance.class)
    }

    public PlantInstance(int plantID, String datePlanted, String stageName) {
        this.PlantID = plantID;
        this.DatePlanted = datePlanted;
        this.StageName = stageName;
    }

    public int getPlantID() {
        return PlantID;
    }

    public void setPlantID(int plantID) {
        this.PlantID = plantID;
    }

    public String getDatePlanted() {
        return DatePlanted;
    }

    public void setDatePlanted(String datePlanted) {
        this.DatePlanted = datePlanted;
    }

    public String getStageName() {
        return StageName;
    }

    public void setStageName(String stageName) {
        this.StageName = stageName;
    }
}
