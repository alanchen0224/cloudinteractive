package com.alanchen.cloudinteractive_alanchen.model;

public class Photo {

    public int albumId;
    public int id;
    public String title;
    public String url;
    public String thumbnailUrl;

    public Photo() { }

    public Photo(int albumId, int id, String title, String url, String thumbnailUrl) {
        this.albumId = albumId;
        this.id = id;
        this.title = title;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
    }
}
