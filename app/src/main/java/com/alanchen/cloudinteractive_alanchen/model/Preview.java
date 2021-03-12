package com.alanchen.cloudinteractive_alanchen.model;

import android.graphics.Bitmap;

public class Preview {

    public int id;
    public String title;
    public Bitmap bitmap;

    public Preview(int id, String title, Bitmap bitmap) {
        this.id = id;
        this.title = title;
        this.bitmap = bitmap;
    }
}
