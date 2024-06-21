package com.example.plant_library.Object;

public class PlantCategory {
    private int CategoryID;
    private String CategoryImage;
    private String CategoryName;


    public PlantCategory() {}

    public PlantCategory(int categoryID, String categoryName, String categoryImage) {
        this.CategoryID = categoryID;
        this.CategoryImage = categoryImage;
        this.CategoryName = categoryName;

    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        this.CategoryID = categoryID;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        this.CategoryName = categoryName;
    }

    public String getCategoryImage() {
        return CategoryImage;
    }

    public void setCategoryImageURL(String categoryImage) {
        this.CategoryImage = categoryImage;
    }
}
