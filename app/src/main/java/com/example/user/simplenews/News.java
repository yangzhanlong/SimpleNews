package com.example.user.simplenews;

public class News {
    private String title;
    private String description;
    private String imgurl;

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getTitle() {
        return title;
    }

    public News(String title, String description, String imgurl) {
        this.title = title;
        this.description = description;
        this.imgurl = imgurl;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
