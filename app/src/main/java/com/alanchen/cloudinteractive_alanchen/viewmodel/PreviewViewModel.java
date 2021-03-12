package com.alanchen.cloudinteractive_alanchen.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.alanchen.cloudinteractive_alanchen.R;
import com.alanchen.cloudinteractive_alanchen.model.Preview;

public class PreviewViewModel extends ViewModel {

    Context context;
    public int id;
    public String title;
    public byte[] bytes;

    public PreviewViewModel(Context context, Preview preview) {
        this.context = context;
        this.id = preview.id;
        this.title = preview.title;
        this.bytes = preview.bytes;
    }

    @BindingAdapter("android:image")
    public static void loadImage(ImageView imageView, byte[] bytes) {
        if ( bytes != null )
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
        else
            imageView.setImageResource(R.drawable.nophoto);
    }

    public void goBack() {
        ((Activity)(context)).finish();
    }
}
