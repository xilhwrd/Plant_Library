package com.example.plant_library.Object;

public class Article {
    private int ArticleID;
    private String ArticleContent, ArticleImage, ArticleTitle, ArtileContentIntro, ArticleDescrip,
            ArticleContent2, ArticleContent3, ArticleContent4;

    public Article( ) {
    }

    public Article(int articleID, String articleContent, String articleImage,
                   String articleTitle, String artileContentIntro, String articleDescription,
                   String articleContent2, String articleContent3, String articleContent4) {
        ArticleID = articleID;
        ArticleContent = articleContent;
        ArticleImage = articleImage;
        ArticleTitle = articleTitle;
        ArtileContentIntro = artileContentIntro;
        ArticleDescrip = articleDescription;
        ArticleContent2 = articleContent2;
        ArticleContent3 = articleContent3;
        ArticleContent4 = articleContent4;
    }

    public int getArticleID() {
        return ArticleID;
    }

    public void setArticleID(int articleID) {
        ArticleID = articleID;
    }

    public String getArticleContent() {
        return ArticleContent;
    }

    public void setArticleContent(String articleContent) {
        ArticleContent = articleContent;
    }

    public String getArticleImage() {
        return ArticleImage;
    }

    public void setArticleImage(String articleImage) {
        ArticleImage = articleImage;
    }

    public String getArticleTitle() {
        return ArticleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        ArticleTitle = articleTitle;
    }

    public String getArtileContentIntro() {
        return ArtileContentIntro;
    }

    public void setArtileContentIntro(String artileContentIntro) {
        ArtileContentIntro = artileContentIntro;
    }

    public String getArticleDescrip() {
        return ArticleDescrip;
    }

    public void setArticleDescrip(String articleDescription) {
        ArticleDescrip = articleDescription;
    }

    public String getArticleContent2() {
        return ArticleContent2;
    }

    public void setArticleContent2(String articleContent2) {
        ArticleContent2 = articleContent2;
    }

    public String getArticleContent3() {
        return ArticleContent3;
    }

    public void setArticleContent3(String articleContent3) {
        ArticleContent3 = articleContent3;
    }

    public String getArticleContent4() {
        return ArticleContent4;
    }

    public void setArticleContent4(String articleContent4) {
        ArticleContent4 = articleContent4;
    }
}
