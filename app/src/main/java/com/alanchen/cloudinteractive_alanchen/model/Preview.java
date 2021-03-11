package com.alanchen.cloudinteractive_alanchen.model;

public class Preview {

    public int id;
    public String title;
    public byte[] bytes;

    public Preview(int id, String title, byte[] bytes) {
        this.id = id;
        this.title = title;
        this.bytes = bytes;
    }
}
