package com.example.myapplication;

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
import android.widget.TextView;



public class MainActivity extends Activity implements View.OnTouchListener {
    TextView tv;
    private int x;
    private int y;
    private PointImageView pointImageView;
    String Touch;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = new TextView(this);
        tv = findViewById(R.id.textView3);
        pointImageView = (PointImageView) findViewById(R.id.imageView);
        pointImageView.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        x = (int) event.getX();
        y = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Touch = "X: " + x + "\n" + "Y: " + y;
            pointImageView.setPoint(new Point(x, y));
        }
        tv.setText(Touch);
        return true;
    }

    public static class PointImageView extends androidx.appcompat.widget.AppCompatImageView{
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
                paint.setStrokeWidth(50f); //Вот оно
                canvas.drawPoint(point.x, point.y, paint);
            }
        }
    }

}






































//////

/*
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



public class MainActivity extends Activity implements View.OnTouchListener {
    TextView tv;
    private int x;
    private int y;
   // private PointImageView pointImageView;
    String Touch;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv = new TextView(this);
        tv.setOnTouchListener(this);
       // setContentView(tv);
        setContentView(new DrawView(this));


        //setContentView(R.layout.activity_main);
        //pointImageView =  findViewById(R.id.imageView);
        //pointImageView.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        x =(int) event.getX();
        y =(int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Touch = "X: " + x + "\n" + "Y: " + y;
           // pointImageView.setPoint(new Point(x, y));
        }

        tv.setText(Touch);
        return true;
    }
/**
    public class PointImageView extends androidx.appcompat.widget.AppCompatImageView{
        private Point point;
        public PointImageView(Context context) {
            super(context);
        }

        public PointImageView(Context context, AttributeSet k
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
                paint.setStrokeWidth(10f); //Вот оно
                canvas.drawPoint(point.x, point.y, paint);
            }
        }

    }
        ///////////////////////////////////////
    static class DrawView extends androidx.appcompat.widget.AppCompatImageView {
        MainActivity Koord = new MainActivity();
        Paint p;
        Rect rect;

        public DrawView(Context context) {
            super(context);
            p = new Paint();
            rect = new Rect();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            p.setColor(Color.RED);
            canvas.drawPoint(Koord.x,Koord.y , p);

        }


    }
}*/
