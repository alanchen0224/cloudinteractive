package com.alanchen.cloudinteractive_alanchen.viewmodel;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.alanchen.cloudinteractive_alanchen.model.Photo;
import com.alanchen.cloudinteractive_alanchen.repository.PhotoInfoRepository;
import com.alanchen.cloudinteractive_alanchen.service.PhotoInfoAPIService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.alanchen.cloudinteractive_alanchen.GridViewActivity.TAG;

public class PhotoViewModel extends ViewModel {

    MutableLiveData<List<Photo>> photoList ;
    public LiveData<List<Photo>> getPhotos() {
        if( photoList == null ) {
            photoList = new MutableLiveData<List<Photo>>();
            loadPhotos();
        }
        return photoList;
    }

    private void loadPhotos() {
        PhotoInfoAPIService photoInfoAPIService = PhotoInfoRepository.getInstance().getAPI();
        Call<ArrayList<Photo>> call = photoInfoAPIService.getPhotosInfo();
        call.enqueue(new Callback<ArrayList<Photo>>() {
            @Override
            public void onResponse(Call<ArrayList<Photo>> call, Response<ArrayList<Photo>> response) {
                Log.d(TAG, "onResponse: " + response);
                photoList.setValue(response.body());
                Log.d(TAG, "photoList: " + photoList.getValue().size());
            }
            @Override
            public void onFailure(Call<ArrayList<Photo>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

}
