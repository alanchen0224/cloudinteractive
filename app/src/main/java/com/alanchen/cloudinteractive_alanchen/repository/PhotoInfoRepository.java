package com.alanchen.cloudinteractive_alanchen.repository;

import com.alanchen.cloudinteractive_alanchen.service.PhotoInfoAPIService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhotoInfoRepository {
    private static PhotoInfoRepository photoInfoRepository = new PhotoInfoRepository();

    private PhotoInfoAPIService photoInfoAPIService;

    private PhotoInfoRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(photoInfoAPIService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        photoInfoAPIService = retrofit.create(PhotoInfoAPIService.class);
    }
    public static PhotoInfoRepository getInstance(){ return photoInfoRepository; }
    public PhotoInfoAPIService getAPI(){
        return photoInfoAPIService;
    }
}
