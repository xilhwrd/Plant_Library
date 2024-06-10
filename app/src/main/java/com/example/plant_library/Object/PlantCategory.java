package com.example.plant_library.Object;

public class PlantCategory {
    private int resourceID;
    private String categoryName;

    public PlantCategory(int resourceID, String categoryName) {
        this.resourceID = resourceID;
        this.categoryName = categoryName;
    }

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
