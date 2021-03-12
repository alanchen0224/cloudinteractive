package com.alanchen.cloudinteractive_alanchen.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface PhotoImgAPIService {
    @GET
    Call<ResponseBody> getPhotoImg(@Url String imgPath);
}
