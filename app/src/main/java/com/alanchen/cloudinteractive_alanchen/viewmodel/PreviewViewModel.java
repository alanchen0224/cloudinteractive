package com.alanchen.cloudinteractive_alanchen.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.alanchen.cloudinteractive_alanchen.R;
import com.alanchen.cloudinteractive_alanchen.model.Preview;

public class PreviewViewModel extends ViewModel {

    Context context;
    public int id;
    public String title;
    public Bitmap bitmap;

    public PreviewViewModel(Context context, Preview preview) {
        this.context = context;
        this.id = preview.id;
        this.title = preview.title;
        this.bitmap = preview.bitmap;
    }

    @BindingAdapter("android:image")
    public static void loadImage(ImageView imageView, Bitmap bitmap) {
        if ( bitmap != null )
            imageView.setImageBitmap(bitmap);
        else
            imageView.setImageResource(R.drawable.nophoto);
    }

    public void goBack() {
        ((Activity)(context)).finish();
    }
}
