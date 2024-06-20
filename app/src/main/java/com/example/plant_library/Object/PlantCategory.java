package com.example.plant_library.Object;

public class PlantCategory {
    private int categoryID;
    private String categoryName;
    private String categoryImageURL;

    public PlantCategory() {}

    public PlantCategory(int categoryID, String categoryName, String categoryImageURL) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.categoryImageURL = categoryImageURL;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImageURL() {
        return categoryImageURL;
    }

    public void setCategoryImageURL(String categoryImageURL) {
        this.categoryImageURL = categoryImageURL;
    }
}
