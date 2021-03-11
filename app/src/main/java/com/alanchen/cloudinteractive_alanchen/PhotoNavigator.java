package com.alanchen.cloudinteractive_alanchen;

import android.view.View;

import com.alanchen.cloudinteractive_alanchen.viewmodel.PhotoViewModel;

public interface PhotoNavigator {
    void onItemClick(View view, PhotoViewModel photoViewModel);
}
