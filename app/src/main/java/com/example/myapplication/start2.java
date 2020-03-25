package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class start2 extends AppCompatActivity {
    Intent intent2;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.start2);
        Button button2 = findViewById(R.id.button20);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }
    public void d(View View)
    {
        intent2 = new Intent (start2.this,start3.class);
        startActivity(intent2);

    }

    public void rd(View View)
    {
        Intent intent1 = new Intent (start2.this,start3.class);
        startActivity(intent1);
        Toast.makeText(
                this, "dsakdadksadas.", Toast.LENGTH_LONG)
                .show();
    }

    public void back(View View)
    {
        finish();
    }
}