package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class start2 extends AppCompatActivity {
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }
    public void d(View View)
    {
        Intent intent = new Intent (start2.this,start3.class);
        startActivity(intent);
    }

    public void rd(View View)
    {
        Intent intent = new Intent (start2.this,start3.class);
        startActivity(intent);
    }

    public void back(View View)
    {
        finish();
    }
}