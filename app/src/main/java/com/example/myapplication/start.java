package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;

public class start<Text1, Text2, a, b, c> extends AppCompatActivity {


    private
    static double a, b ,c;

    EditText Text1, Text2, Text3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Text1 = (EditText) findViewById(R.id.editText2);
        Text2 = (EditText) findViewById(R.id.editText3);
        Text3 = (EditText) findViewById(R.id.editText4);
    }



    public void Back(View view) {
        finish();

    }

    public void next(View view) {

            try {
                a = Integer.parseInt(Text1.getText().toString());
                b = Integer.parseInt(Text2.getText().toString());
                c = Integer.parseInt(Text1.getText().toString());


            } catch (NumberFormatException e) {
            a = 0;
            b = 0;
        }
        Intent intent = new Intent (start.this,start1.class);
        startActivity(intent);
        }
    }

