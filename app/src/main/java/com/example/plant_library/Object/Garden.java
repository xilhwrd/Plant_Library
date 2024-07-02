package com.example.plant_library.Object;

    public class Garden {
        private int GardenID;
        private int PlantID;
        private String DatePlanted;

        public Garden() {
            // Default constructor required for calls to DataSnapshot.getValue(Garden.class)
        }

        public Garden(int gardenID, int plantID, String datePlanted) {
            this.GardenID = gardenID;
            this.PlantID = plantID;
            this.DatePlanted = datePlanted;
        }

        public int getGardenID() {
            return GardenID;
        }

        public void setGardenID(int gardenID) {
            GardenID = gardenID;
        }

        public int getPlantID() {
            return PlantID;
        }

        public void setPlantID(int plantID) {
            PlantID = plantID;
        }

        public String getDatePlanted() {
            return DatePlanted;
        }

        public void setDatePlanted(String datePlanted) {
            DatePlanted = datePlanted;
        }
    }


