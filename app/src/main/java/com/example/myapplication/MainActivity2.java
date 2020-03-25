package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

@SuppressLint("Registered")
public class MainActivity2 extends Activity implements View.OnTouchListener {
    TextView tv;
    TextView tv2;
    private TextView textshow;
    private int x;
    private int y;
    private int x1;
    private int y1;
    private PointImageView pointImageView;
    String Touch;
    String str;
    String saved;
    final String LOG_TAG = "myLogs";

    final String FILENAME = "file";

    final String DIR_SD = "MyFiles";
    final String FILENAME_SD = "fileSD";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = new TextView(this);
        tv2 = new TextView(this);
        tv = findViewById(R.id.textView1);
        tv2 = findViewById(R.id.textView12);
        pointImageView = (PointImageView) findViewById(R.id.imageView);
        pointImageView.setOnTouchListener(this);


    }

    public void read(View view) {
        try {
            FileInputStream fileInput = openFileInput("koord.txt");
            InputStreamReader reader = new InputStreamReader(fileInput);
            BufferedReader buffer = new BufferedReader(reader);
            StringBuilder strBufffer = new StringBuilder();
            String lines  ;
            lines= buffer.readLine();
            x1 = Integer.parseInt(lines);
            while ((lines = buffer.readLine()) != null) {
                strBufffer.append(lines).append("\n");

                y1= Integer.parseInt(lines);
            }

            pointImageView.setPoint(new Point(x1, y1));
            saved= "X: " + x1 + "\n" + "Y: " + y1;
            tv2.setText(saved);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(View view) throws FileNotFoundException {
        // String myTxt = edit.getText().toString();
        try {
            FileOutputStream fileOutput = openFileOutput("koord.txt", MODE_PRIVATE);
            fileOutput.write(str.getBytes());
            fileOutput.close();
            Toast.makeText(MainActivity2.this, "Координаты сохранены", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        x = (int) event.getX();
        y = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Touch = "X: " + x + "\n" + "Y: " + y;
            pointImageView.setPoint(new Point(x, y));
            str = x + "\n" + y;

        }
        tv.setText(Touch);

        return true;
    }

    public static class PointImageView extends androidx.appcompat.widget.AppCompatImageView {
        private Point point;

        public PointImageView(Context context) {
            super(context);
        }

        public PointImageView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public PointImageView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        public void setPoint(Point point) {
            this.point = point;
            invalidate(); //Вот он
        }

        @Override
        public void draw(Canvas canvas) {
            super.draw(canvas);
            if (point != null) {
                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setStrokeWidth(30f); //Вот оно
                canvas.drawPoint(point.x, point.y, paint);
            }


        }
    }
}