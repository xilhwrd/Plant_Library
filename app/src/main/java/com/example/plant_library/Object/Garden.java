package com.example.plant_library.Object;

import java.util.Map;

public class Garden {
        private Map<String, Boolean> PlantID;
        private String DatePlanted;

        public Garden() {
            // Default constructor required for calls to DataSnapshot.getValue(Garden.class)
        }

    public Garden(Map<String, Boolean> plantID, String datePlanted) {
        PlantID = plantID;
        DatePlanted = datePlanted;
    }

    public Map<String, Boolean> getPlantID() {
        return PlantID;
    }

    public void setPlantID(Map<String, Boolean> plantID) {
        PlantID = plantID;
    }

    public String getDatePlanted() {
        return DatePlanted;
    }

    public void setDatePlanted(String datePlanted) {
        DatePlanted = datePlanted;
    }
}


