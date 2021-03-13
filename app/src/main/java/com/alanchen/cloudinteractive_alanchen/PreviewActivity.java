package com.alanchen.cloudinteractive_alanchen;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alanchen.cloudinteractive_alanchen.databinding.ActivityPreViewBinding;
import com.alanchen.cloudinteractive_alanchen.model.Preview;
import com.alanchen.cloudinteractive_alanchen.service.PhotoImgAPIService;
import com.alanchen.cloudinteractive_alanchen.utils.Downloader;
import com.alanchen.cloudinteractive_alanchen.viewmodel.PreviewViewModel;

import java.io.File;

import static com.alanchen.cloudinteractive_alanchen.GridViewActivity.TAG;
import static com.alanchen.cloudinteractive_alanchen.GridViewActivity.tempPathMap;

public class PreviewActivity extends AppCompatActivity {

    int id;
    String title;
    String thumbnailUrl;
    byte[] bytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        thumbnailUrl = getIntent().getStringExtra("thumbnailUrl") ;

        if( Downloader.checkIfCacheImg(id) ) {
            File file = new File(this.getCacheDir(), "/"+ tempPathMap.get(id));
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            generateView(bitmap);
        }else
            Downloader.loadImage(id, thumbnailUrl, loadImageCallBack);

    }

    void generateView(Bitmap bitmap) {
        ActivityPreViewBinding activityPreViewBinding = DataBindingUtil.setContentView(this,R.layout.activity_pre_view);
        PreviewViewModel previewViewModel = new PreviewViewModel(new Preview(id, title, bitmap));
        previewViewModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!PreviewActivity.this.isDestroyed()) {
                    finish();
                }
            }
        });
        activityPreViewBinding.setViewmodel(previewViewModel);
        activityPreViewBinding.executePendingBindings();
    }

    private final Downloader.LoadImageCallBack loadImageCallBack = new Downloader.LoadImageCallBack() {
        @Override
        public void onLoadedImage(int id, byte[] bytes) {
            if( bytes != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                generateView(bitmap);
            }else {
                generateView(null);
            }
        }
    };

}
