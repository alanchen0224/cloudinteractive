package com.alanchen.cloudinteractive_alanchen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alanchen.cloudinteractive_alanchen.service.PhotoInfoAPIService;


public class MainActivity extends AppCompatActivity {
    public static String TAG = "System.out";
    PhotoInfoAPIService photoInfoAPIService;

    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClassName(getApplicationContext().getPackageName(), "com.alanchen.cloudinteractive_alanchen.GridViewActivity");
                startActivity(it);
            }
        });
    }
}
