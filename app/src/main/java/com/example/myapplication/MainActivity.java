package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.content.pm.ActivityInfo;


public class MainActivity extends AppCompatActivity {

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void Click(View view) {

        //process button click
    finish();
    }

    public void Click2(View view) {
        Intent intent = new Intent (MainActivity.this,Main2Activity.class);
        startActivity(intent);

    }

    public void Set(View view) {
        Intent intent = new Intent (MainActivity.this,settings.class);
        startActivity(intent);
    }

    public void start(View view) {
        Intent intent = new Intent (MainActivity.this,start.class);
        startActivity(intent);
    }
}
