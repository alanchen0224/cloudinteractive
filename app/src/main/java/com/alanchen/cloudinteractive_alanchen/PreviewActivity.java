package com.alanchen.cloudinteractive_alanchen;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.alanchen.cloudinteractive_alanchen.databinding.ActivityPreViewBinding;
import com.alanchen.cloudinteractive_alanchen.model.Preview;
import com.alanchen.cloudinteractive_alanchen.service.PhotoImgAPIService;
import com.alanchen.cloudinteractive_alanchen.utils.Downloader;
import com.alanchen.cloudinteractive_alanchen.viewmodel.PreviewViewModel;

import java.io.File;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
        title = getIntent().getStringExtra("title") ;
        thumbnailUrl = getIntent().getStringExtra("thumbnailUrl") ;

        if( Downloader.checkIfCacheImg(id) ) {
            File file = new File(this.getCacheDir(), "/"+ tempPathMap.get(id));
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            generateView(bitmap);
        }else
            loadImage(thumbnailUrl);

    }

    void generateView(Bitmap bitmap) {
        ActivityPreViewBinding activityPreViewBinding = DataBindingUtil.setContentView(this,R.layout.activity_pre_view);
        PreviewViewModel previewViewModel = new PreviewViewModel(this, new Preview(id, title, bitmap));
        activityPreViewBinding.setViewmodel(previewViewModel);
        activityPreViewBinding.executePendingBindings();
    }

    void loadImage (String thumbnailUrl) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://via.placeholder.com/150/35cedf/").build();
        PhotoImgAPIService photoImgAPIService = retrofit.create(PhotoImgAPIService.class);
        Call<ResponseBody> call = photoImgAPIService.getPhotoImg(thumbnailUrl);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    bytes = response.body().bytes();
                }catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if( bytes != null)
                        generateView(BitmapFactory.decodeByteArray(bytes,0,bytes.length));
                    else
                        generateView(null);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure "+t.getMessage());
                generateView(null);
            }
        });

    }





}
