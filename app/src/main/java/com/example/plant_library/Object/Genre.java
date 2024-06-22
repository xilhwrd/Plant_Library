package com.example.plant_library.Object;

public class Genre {
    private int GenreID;
    private String GenreName, GenreImage;

    public Genre() {
    }

    public Genre(int genreID, String genreName, String genreImage) {
        GenreID = genreID;
        GenreName = genreName;
        GenreImage = genreImage;
    }

    public int getGenreID() {
        return GenreID;
    }

    public void setGenreID(int genreID) {
        GenreID = genreID;
    }

    public String getGenreName() {
        return GenreName;
    }

    public void setGenreName(String genreName) {
        GenreName = genreName;
    }

    public String getGenreImage() {
        return GenreImage;
    }

    public void setGenreImage(String genreImage) {
        GenreImage = genreImage;
    }
}
