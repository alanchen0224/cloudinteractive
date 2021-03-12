package com.alanchen.cloudinteractive_alanchen.viewmodel;


import android.arch.lifecycle.ViewModel;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.alanchen.cloudinteractive_alanchen.PhotoNavigator;
import com.alanchen.cloudinteractive_alanchen.R;
import com.alanchen.cloudinteractive_alanchen.model.Photo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;

public class PhotoViewModel extends ViewModel {

    public int albumId;
    public int id;
    public String title;
    public String url;
    public String thumbnailUrl;

    PhotoNavigator photoNavigator;
    public void setPhotoNavigator(PhotoNavigator photoNavigator){
        this.photoNavigator = photoNavigator;
    }


    public PhotoViewModel(Photo photo) {
        this.albumId = photo.albumId;
        this.id = photo.id;
        this.title = photo.title;
        this.url = photo.url;
        this.thumbnailUrl = photo.thumbnailUrl;
    }

    @BindingAdapter("android:image")
    public static void loadImage(ImageView imageView, String path) {
        GlideUrl url = new GlideUrl(path, new LazyHeaders.Builder()
                .addHeader("User-Agent", "alanchen-fortest")
                .build());
        Glide.with(imageView.getContext())
            .asBitmap()
            .load(url)
            .apply(new RequestOptions().placeholder(R.drawable.nophoto))
            .into(imageView);
    }

    public void itemClick(View view, PhotoViewModel photoViewModel) {
        photoNavigator.onItemClick(view, photoViewModel);
    }

}
