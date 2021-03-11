package com.alanchen.cloudinteractive_alanchen.service;

import com.alanchen.cloudinteractive_alanchen.model.Photo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PhotoInfoAPIService {
    @GET("/photos")
    Call<ArrayList<Photo>> getPhotosInfo();
}
