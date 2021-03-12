package com.alanchen.cloudinteractive_alanchen.utils;

import android.util.Log;

import static com.alanchen.cloudinteractive_alanchen.GridViewActivity.TAG;
import static com.alanchen.cloudinteractive_alanchen.GridViewActivity.tempPathMap;

public class Downloader {

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

}
