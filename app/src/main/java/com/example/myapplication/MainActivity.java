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
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class MainActivity extends Activity implements View.OnTouchListener {
    TextView tv;
    TextView tv2;
    EditText Beacons;
    Button Ok;
    Button Plus;
    Button Go;
    private int getX;
    private int getY;
    private PointImageView pointImageView;
    private Math Math;
    private int pointImageHeight = 0;
    private int pointImageWidth = 0;
    String Touch = "";
    String MoveTouch = "";
    public int Key = 0;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = new TextView(this);
        tv2 = new TextView(this);
        tv = findViewById(R.id.textView3);
        tv2 = findViewById(R.id.textView);
        Beacons = findViewById(R.id.editText3);
        Ok = findViewById(R.id.button);
        Plus = findViewById(R.id.button2);
        Go = findViewById(R.id.button3);
        //tv.setOnTouchListener(this);
        //setContentView(tv);

        pointImageView = (PointImageView) findViewById(R.id.imageView);

        pointImageView.setOnTouchListener(this);


        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Beacons.getText().toString().equals("") || Beacons.getText().toString().equals("0"))
                {
                    tv.setText("Введите количество маяков");
                }
                else
                {
                    Key += Integer.parseInt(Beacons.getText().toString());
                    Beacons.setKeyListener(null);
                    v.setClickable(false);
                    Beacons.setText("");
                }
            }
        });

        Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Key += 1;
            }
        });

        Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////////////////////////////////////////////////////
                tv.setText("Coming soon");
            }
        });

    }


    /*public void AddBeacon(View view) {
        Key += 1;
    }*/

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        getX = (int) event.getX();
        getY = (int) event.getY();
        int CapturedPointIndex = 0;
        ///int A = Integer.parseInt(Beacons.getText().toString());
        // = A;
        boolean Captured = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if (pointImageView.PointList.size() !=  Key) {
                    Touch = "X: " + (getX) + "\n" + "Y: " +  (abs(getY - 1850))+ "\n";
                    pointImageView.PointList.add(new Point(getX, getY));
                    pointImageView.invalidateImage();
                }
                else {
                  Touch = "X: " + (getX - 2) + "\n" + "Y: " + (abs(getY - 1850))+ "\n";
                }
                //  pointImageView.setPoint(new Point(x, y));
                //tv.setText(null);
                //tv.setText("");
                tv.setText(Touch);
                //Touch = "";
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                for (int i = 0; i < pointImageView.PointList.size(); i++) {
                    int offsetX = abs(getX - pointImageView.PointList.get(i).x);
                    int offsetY = abs(getY - pointImageView.PointList.get(i).y);
                    if (offsetX < 30 && offsetY < 30) {
                        if (getX < 0 || getX > pointImageView.getMaxWidth()) {
                            pointImageView.PointList.get(i).x = 0;
                        } else if (getY < 0 || getY > pointImageView.getMaxHeight()) {
                            pointImageView.PointList.get(i).y = 0;
                        } else {
                            pointImageView.PointList.get(i).x = getX;
                            pointImageView.PointList.get(i).y = getY;
                        }
                        pointImageView.invalidateImage();
                    }
                }
                MoveTouch = "X: " + (getX - 2) + "\n" + "Y: " + (abs(getY - 1850))+ "\n";
                tv.setText(MoveTouch);
                //Toast.makeText(this,"ACTION MOVE",Toast.LENGTH_SHORT).show();
                break;
            }
            case MotionEvent.ACTION_UP: {
                if (Captured) {
                    pointImageView.PointList.get(CapturedPointIndex).x = getX;
                    pointImageView.PointList.get(CapturedPointIndex).y = getY;
                    pointImageView.invalidateImage();
                }
                 // pointImageView.PointList.get(CapturedPointIndex).x = getX;
                  //pointImageView.PointList.get(CapturedPointIndex).y = getY;
                break;
            }
        }
        return true;
    }



    public static class PointImageView extends androidx.appcompat.widget.AppCompatImageView {
        //public final int MaxSatCount = Integer.parseInt(Beacons.getText().toString());
        //public int MaxSatCount = 0;
        public ArrayList<Point> PointList = new ArrayList<Point>();
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


        public void invalidateImage() {
            invalidate();
        }

        /*public void setPoint(Point point) {
            this.point = point;
            invalidate(); //Вот он
        }*/

        @Override
        public void draw(Canvas canvas) {
            super.draw(canvas);
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(30f);
            if (point != null) {
              /*Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setStrokeWidth(30f); //Вот оно
                canvas.drawPoint(point.x, point.y, paint);
                for (Point p : PointList)
                {
                    canvas.drawPoint(p.x, p.y, paint);
                    invalidate();
                }*/
            }
            for (Point p : PointList) {
                canvas.drawPoint(p.x, p.y, paint);
                invalidate();
            }
        }
    }
}


