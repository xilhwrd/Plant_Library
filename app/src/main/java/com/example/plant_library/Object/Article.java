package com.example.plant_library.Object;

public class Article {
    private int resourceID;
    private String articleName;

    public Article(int resourceID, String articleName) {
        this.resourceID = resourceID;
        this.articleName = articleName;
    }

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }
}
