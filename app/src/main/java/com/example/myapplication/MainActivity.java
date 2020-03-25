package com.example.myapplication;


import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnTouchListener {
    TextView tv;
    TextView tv2;
    private int getX;
    private int getY;
    private PointImageView pointImageView;
    private int pointImageHeight = 0;
    private int pointImageWidth = 0;
    String Touch = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = new TextView(this);
        tv2 = new TextView(this);
        //tv.setOnTouchListener(this);
        //setContentView(tv);
        tv = findViewById(R.id.textView3);
        tv2 = findViewById(R.id.textView);
        pointImageView = (PointImageView) findViewById(R.id.imageView);

        pointImageView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        getX = (int) event.getX();
        getY = (int) event.getY();
        int CapturedPointIndex = 0;
        boolean Captured = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {

                if (pointImageView.PointList.size() != pointImageView.MaxSatCount) {
                    Touch += "X: " + getX + "\n" + "Y: " + getY + "\n";
                    pointImageView.PointList.add(new Point(getX, getY));
                    pointImageView.invalidateImage();
                }
                //  pointImageView.setPoint(new Point(x, y));
                tv.setText(Touch);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                for (int i = 0; i < pointImageView.PointList.size(); i++) {
                    int offsetX = java.lang.Math.abs(getX - pointImageView.PointList.get(i).x);
                    int offsetY = java.lang.Math.abs(getY - pointImageView.PointList.get(i).y);
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
                tv2.setText(getX + "\n" + getY);
                //Toast.makeText(this,"ACTION MOVE",Toast.LENGTH_SHORT).show();
                break;
            }
            case MotionEvent.ACTION_UP: {
                if (Captured) {
                    pointImageView.PointList.get(CapturedPointIndex).x = getX;
                    pointImageView.PointList.get(CapturedPointIndex).y = getY;
                    pointImageView.invalidateImage();
                }
                //  pointImageView.PointList.get(CapturedPointIndex).x = getX;
                //  pointImageView.PointList.get(CapturedPointIndex).y = getY;
                break;
            }
        }
        return true;
    }

    public static class PointImageView extends androidx.appcompat.widget.AppCompatImageView {
        public final int MaxSatCount = 5;
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

        public void setPoint(Point point) {
            this.point = point;
            invalidate(); //Вот он
        }

        @Override
        public void draw(Canvas canvas) {
            super.draw(canvas);
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(30f);
            if (point != null) {
           /*   Paint paint = new Paint();
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


































