package com.example.plant_library.Object;

public class GardenInformation {

    String Heading, Content;
    int TitleImage;
    boolean visibility;

    public GardenInformation(String heading, String content, int titleImage) {
        this.Heading = heading;
        this.Content = content;
        this.TitleImage = titleImage;
        this.visibility = false;
    }

    public String getHeading() {
        return Heading;
    }

    public void setHeading(String heading) {
        Heading = heading;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getTitleImage() {
        return TitleImage;
    }

    public void setTitleImage(int titleImage) {
        TitleImage = titleImage;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }
}
