package com.alanchen.cloudinteractive_alanchen.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.alanchen.cloudinteractive_alanchen.R;
import com.alanchen.cloudinteractive_alanchen.model.Preview;

public class PreviewViewModel extends ViewModel {

    public int id;
    public String title;
    public Bitmap bitmap;
    public View.OnClickListener mOnClickListener;

    public PreviewViewModel(Preview preview) {
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

    public void setOnClickListener(View.OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

}
