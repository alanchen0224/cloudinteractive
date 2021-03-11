package com.alanchen.cloudinteractive_alanchen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alanchen.cloudinteractive_alanchen.adapter.PhotoAdapter;
import com.alanchen.cloudinteractive_alanchen.model.Photo;
import com.alanchen.cloudinteractive_alanchen.repository.PhotoInfoRepository;
import com.alanchen.cloudinteractive_alanchen.service.PhotoInfoAPIService;
import com.alanchen.cloudinteractive_alanchen.viewmodel.PhotoViewModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GridViewActivity extends AppCompatActivity implements PhotoNavigator{
    public static String TAG = "System.out";
    Dialog progressDialog;
    PhotoInfoAPIService photoInfoAPIService;
    ArrayList<Photo> photoArrayList;

    RecyclerView photoInfoRV;
    List<PhotoViewModel> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        photoInfoRV = findViewById(R.id.photoInfoRV);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,4);
        photoInfoRV.setLayoutManager(gridLayoutManager);

        init();
    }

    void init(){
        showProgressDialog();
        getPhotoDatas();
    }

    void getPhotoDatas(){

        photoInfoAPIService = PhotoInfoRepository.getInstance().getAPI();
        Call<ArrayList<Photo>> call = photoInfoAPIService.getPhotosInfo();
        call.enqueue(new Callback<ArrayList<Photo>>() {
            @Override
            public void onResponse(Call<ArrayList<Photo>> call, Response<ArrayList<Photo>> response) {
                Log.d(TAG, "onResponse: " + response);
                photoArrayList = response.body();
                generateView();
            }
            @Override
            public void onFailure(Call<ArrayList<Photo>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT);
            }
        });
    }

    void generateView() {
        for(int i=0; i<photoArrayList.size(); i++){
            PhotoViewModel photoViewModel = new PhotoViewModel(photoArrayList.get(i));
            photoViewModel.setPhotoNavigator(this);
            data.add(photoViewModel);
        }
        PhotoAdapter photoAdapter = new PhotoAdapter(data,GridViewActivity.this);
        photoInfoRV.setAdapter(photoAdapter);
        closeProgressDialog();
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

    @Override
    public void onItemClick(View view, PhotoViewModel photoViewModel) {

        RelativeLayout relativeLayout = (RelativeLayout) view;
        ImageView imageView = relativeLayout.findViewById(R.id.pImage);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ((BitmapDrawable)imageView.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();

        Intent it = new Intent();
        it.setClassName(this,"com.alanchen.cloudinteractive_alanchen.PreviewActivity");
        it.putExtra("id", photoViewModel.id);
        it.putExtra("title", photoViewModel.title);
        it.putExtra("bytes", bytes);
        startActivity(it);
    }
}
