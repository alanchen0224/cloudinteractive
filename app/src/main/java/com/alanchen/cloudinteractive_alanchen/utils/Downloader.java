package com.alanchen.cloudinteractive_alanchen.utils;

import android.util.Log;
import com.alanchen.cloudinteractive_alanchen.service.PhotoImgAPIService;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.alanchen.cloudinteractive_alanchen.GridViewActivity.TAG;
import static com.alanchen.cloudinteractive_alanchen.GridViewActivity.tempPathMap;

public class Downloader {
    // -- Private Constant --
    private static final String URL_DEFAULT = "https://via.placeholder.com/";

    public static boolean checkIfCacheImg(int id) {
        if( tempPathMap.get(id) != null) {
            Log.d(TAG,"has created");
            return true;
        }
        else{
            Log.d(TAG,"nonexistence");
            return false;
        }
    }

    public static void loadImage (final int id, final String thumbnailUrl, final LoadImageCallBack callBack) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL_DEFAULT).build();
        PhotoImgAPIService photoImgAPIService = retrofit.create(PhotoImgAPIService.class);
        Call<ResponseBody> call = photoImgAPIService.getPhotoImg(thumbnailUrl);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e(TAG, "onResponse " + response);
                try {
                    callBack.onLoadedImage(id, response.body().bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Close the body
                response.body().close();
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure " + t.getMessage());
                if( callBack != null) {
                    callBack.onLoadedImage(id, null);
                }
            }
        });
    }

    public interface LoadImageCallBack{
        void onLoadedImage(int id, byte[] bytes);
    }

}
