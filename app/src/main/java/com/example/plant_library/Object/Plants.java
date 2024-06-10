package com.example.plant_library.Object;

public class Plants {
    private int resourceID, resourceSun, resourceWater, resourceHard;
    private String plantName;

    public Plants(int resourceID, int resourceSun, int resourceWater, int resourceHard, String plantName) {
        this.resourceID = resourceID;
        this.resourceSun = resourceSun;
        this.resourceWater = resourceWater;
        this.resourceHard = resourceHard;
        this.plantName = plantName;
    }

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public int getResourceSun() {
        return resourceSun;
    }

    public void setResourceSun(int resourceSun) {
        this.resourceSun = resourceSun;
    }

    public int getResourceWater() {
        return resourceWater;
    }

    public void setResourceWater(int resourceWater) {
        this.resourceWater = resourceWater;
    }

    public int getResourceHard() {
        return resourceHard;
    }

    public void setResourceHard(int resourceHard) {
        this.resourceHard = resourceHard;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }
}
