package com.alanchen.cloudinteractive_alanchen;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import com.alanchen.cloudinteractive_alanchen.databinding.ActivityPreViewBinding;
import com.alanchen.cloudinteractive_alanchen.model.Preview;
import com.alanchen.cloudinteractive_alanchen.viewmodel.PreviewViewModel;

public class PreviewActivity extends AppCompatActivity {

    int id;
    String title;
    byte[] bytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title") ;
        bytes = getIntent().getByteArrayExtra("bytes");

        ActivityPreViewBinding activityPreViewBinding = DataBindingUtil.setContentView(this,R.layout.activity_pre_view);
        PreviewViewModel previewViewModel = new PreviewViewModel(this, new Preview(id, title, bytes));
        activityPreViewBinding.setViewmodel(previewViewModel);
        activityPreViewBinding.executePendingBindings();

    }

}
