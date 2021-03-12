package com.alanchen.cloudinteractive_alanchen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alanchen.cloudinteractive_alanchen.adapter.PhotoAdapter2;
import com.alanchen.cloudinteractive_alanchen.model.Photo;
import com.alanchen.cloudinteractive_alanchen.viewmodel.PhotoViewModel;

import java.util.HashMap;
import java.util.List;

public class GridViewActivity extends AppCompatActivity {
    public static String TAG = "System.out";
    public static HashMap<Integer, String> tempPathMap = new HashMap<>();
    Dialog progressDialog;
    RecyclerView photoInfoRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        showProgressDialog();
        photoInfoRV = findViewById(R.id.photoInfoRV);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,4);
        photoInfoRV.setLayoutManager(gridLayoutManager);

        PhotoViewModel photoViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);

        photoViewModel.getPhotos().observe(this, new Observer<List<Photo>>() {
            @Override
            public void onChanged(@Nullable List<Photo> photos) {
                PhotoAdapter2 photoAdapter2 = new PhotoAdapter2(GridViewActivity.this, photos, onItemClick);
                photoInfoRV.setAdapter(photoAdapter2);
                closeProgressDialog();
            }
        });

    }

    private void showProgressDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LinearLayout layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.progress, null);
        builder.setCancelable(false);
        builder.setView(layout);
        progressDialog = builder.create();
        progressDialog.show();
    }

    public void closeProgressDialog() {
        if(progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private  PhotoAdapter2.OnItemClick onItemClick = new PhotoAdapter2.OnItemClick() {
        @Override
        public void onClick(ImageView imgView, Photo photo) {
            Intent it = new Intent(GridViewActivity.this, PreviewActivity.class);
            it.putExtra("id", photo.id);
            it.putExtra("title", photo.title);
            it.putExtra("thumbnailUrl", photo.thumbnailUrl);
            startActivity(it);
        }
    };
}
