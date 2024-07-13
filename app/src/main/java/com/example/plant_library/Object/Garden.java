package com.example.plant_library.Object;

import java.util.List;
import java.util.Map;

public class Garden {
    private List<PlantInstance> plants;

    public Garden() {
        // Default constructor required for calls to DataSnapshot.getValue(Garden.class)
    }

    public Garden(List<PlantInstance> plants) {
        this.plants = plants;
    }

    public List<PlantInstance> getPlants() {
        return plants;
    }

    public void setPlants(List<PlantInstance> plants) {
        this.plants = plants;
    }

}


