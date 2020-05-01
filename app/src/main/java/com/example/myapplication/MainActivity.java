package com.example.myapplication;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

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
    private MatrixMath MatrixMath;
    private int pointImageHeight = 0;
    private int pointImageWidth = 0;
    private int ScreenStep = 100;
    String Touch = "";
    String MoveTouch = "";
    public int Key = 0;
    private ImageView drawingImageView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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
       ImageView imageView = findViewById(R.id.imageView);
// плохой код. только для демонстрации
       // imageView.setImageDrawable(new DrawView(this));
        pointImageView = (PointImageView) findViewById(R.id.imageView);
        pointImageView.setOnTouchListener(this);
        //////////////////////////

        /*
        drawingImageView = (ImageView) this.findViewById(R.id.imageView);
        Bitmap bitmap = Bitmap.createBitmap((int) getWindowManager()
                .getDefaultDisplay().getWidth(), (int) getWindowManager()
                .getDefaultDisplay().getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawingImageView.setImageBitmap(bitmap);

        Paint paint = new Paint();
        paint.setColor(Color.DKGRAY);
        paint.setStrokeWidth(3);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        pointImageWidth = size.x;
        pointImageHeight = size.y;



        for (int i = 0; i <= pointImageHeight; i += ScreenStep) // Вертикальные линии
            canvas.drawLine(0, i, pointImageWidth, i, paint);
        for (int i = 0; i <= pointImageWidth; i += ScreenStep) // Горизонтальные линии
            canvas.drawLine(i, 0, i, pointImageHeight, paint);
    */

        ////////////////////////////////

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
                   // Beacons.setText("");
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
               // tv.setText("Какая-нибудь хуйня");
                MatrixMath matrixMath = new MatrixMath();
                double [][] Gdop;
                Toast.makeText(getApplicationContext(),String.valueOf(pointImageView.getWidth()) + " " +String.valueOf(pointImageView.getHeight()),Toast.LENGTH_LONG).show();
                Gdop = matrixMath.main(pointImageView.PointList,pointImageView.getWidth(),pointImageView.getHeight());
                pointImageView.setGdop(Gdop);
                pointImageView.redrawGDOP= true;
               // pointImageView.DrawGdop(math.main(pointImageView.PointList));
               // pointImageView.DrawBitMap();
                //pointImageView.DrawGdop(Gdop);
                //PointImageView.DrawGdop(Gdop);
                //pointImageView.invalidateImage();
                //math.main(pointImageView.PointList);

            }
        });
    }

    /*    public class MySurfaceThread extends Thread {

            private SurfaceHolder myThreadSurfaceHolder;
            private MySurfaceView myThreadSurfaceView;
            private boolean myThreadRun = false;

            public MySurfaceThread(SurfaceHolder surfaceHolder,
                                   MySurfaceView surfaceView) {
                myThreadSurfaceHolder = surfaceHolder;
                myThreadSurfaceView = surfaceView;
            }

            public void setRunning(boolean b) {
                myThreadRun = b;
            }

            @Override
            public void run() {
                // super.run();
                while (myThreadRun) {
                    Canvas c = null;
                    try {
                        c = myThreadSurfaceHolder.lockCanvas(null);
                        synchronized (myThreadSurfaceHolder) {
                            myThreadSurfaceView.onDraw(c);
                        }
                    } finally {
                        // do this in a finally so that if an exception is thrown
                        // during the above, we don't leave the Surface in an
                        // inconsistent state
                        if (c != null) {
                            myThreadSurfaceHolder.unlockCanvasAndPost(c);
                        }
                    }
                }
            }
        }

        public class MySurfaceView extends SurfaceView implements
                SurfaceHolder.Callback {

            private MySurfaceThread thread;

            @Override
            protected void onDraw(Canvas canvas) {
                // super.onDraw(canvas);
                if (drawing) {
                    canvas.drawCircle(initX, initY, radius, paint);
                }
            }

            @Override
            public boolean onTouchEvent(MotionEvent event) {
                // return super.onTouchEvent(event);
                int action = event.getAction();
                if (action == MotionEvent.ACTION_MOVE) {
                    float x = event.getX();
                    float y = event.getY();
                    radius = (float) Math.sqrt(Math.pow(x - initX, 2)
                            + Math.pow(y - initY, 2));
                } else if (action == MotionEvent.ACTION_DOWN) {
                    initX = event.getX();
                    initY = event.getY();
                    radius = 1;
                    drawing = true;
                } else if (action == MotionEvent.ACTION_UP) {
                    drawing = false;
                }

                return true;
            }

            public MySurfaceView(Context context) {
                super(context);
                init();
            }

            public MySurfaceView(Context context, AttributeSet attrs) {
                super(context, attrs);
                init();
            }

            public MySurfaceView(Context context, AttributeSet attrs, int defStyle) {
                super(context, attrs, defStyle);
                init();
            }

            private void init() {
                getHolder().addCallback(this);

                setFocusable(true); // make sure we get key events

                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(3);
                paint.setColor(Color.WHITE);
            }

            @Override
            public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2,
                                       int arg3) {
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                thread = new MySurfaceThread(getHolder(), this);
                thread.setRunning(true);
                thread.start();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                thread.setRunning(false);
                while (retry) {
                    try {
                        thread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }
        }*/


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
                    Touch = "X: " + (getX - 2) + "\n" + "Y: " + (abs(getY - 1850)) + "\n";
                    pointImageView.PointList.add(new Point(getX, getY));
                    pointImageView.invalidateImage();
                }
                else {
                  Touch = "X: " + (getX/* - 2*/) + "\n" + "Y: " + (abs(getY - 1850))+ "\n";
                }
                //  pointImageView.setPoint(new Point(x, y));
                //tv.setText(null);
                //tv.setText("");
                tv.setText(Touch);
                //Touch = "";
                break;
            }
            //остался баг с пожиранием маяков
            case MotionEvent.ACTION_MOVE: {
                for (int i = 0; i < pointImageView.PointList.size(); i++) {
                    int offsetX = abs(getX - pointImageView.PointList.get(i).x);
                    int offsetY = abs(getY - pointImageView.PointList.get(i).y);
                    if (offsetX < 50 && offsetY < 50) {
                        Captured = true;
                        CapturedPointIndex = i;
                        if (getX < 0 ) {
                            pointImageView.PointList.get(i).x = 0;
                        }
                        else if(getX > pointImageView.getWidth())
                        {
                            pointImageView.PointList.get(i).x = pointImageView.getWidth();
                            pointImageView.PointList.get(i).y = getY;
                        }
                        else if (getY < 0 ) {
                            pointImageView.PointList.get(i).y = 0;
                        }
                        else if (getY > pointImageView.getHeight())
                        {
                            pointImageView.PointList.get(i).y = pointImageView.getHeight();
                            pointImageView.PointList.get(i).x = getX;
                        }
                        else
                         {
                            pointImageView.PointList.get(i).x = getX;
                            pointImageView.PointList.get(i).y = getY;
                        }
                    }
                    pointImageView.invalidateImage();
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
                 // pointImageView.PointList.get(CapturedPointIndex).y = getY;
                break;
            }
        }
        return true;
    }

    class DrawView extends SurfaceView implements SurfaceHolder.Callback {

        private DrawThread drawThread;

        public DrawView(Context context) {
            super(context);
            getHolder().addCallback(this);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {

        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            drawThread = new DrawThread(getHolder());
            drawThread.setRunning(true);
            drawThread.start();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            boolean retry = true;
            drawThread.setRunning(false);
            while (retry) {
                try {
                    drawThread.join();
                    retry = false;
                } catch (InterruptedException e) {
                }
            }
        }

        class DrawThread extends Thread {

            private boolean running = false;
            private SurfaceHolder surfaceHolder;

            public DrawThread(SurfaceHolder surfaceHolder) {
                this.surfaceHolder = surfaceHolder;
            }

            public void setRunning(boolean running) {
                this.running = running;
            }

            @Override
            public void run() {
                Canvas canvas;
                while (running) {
                    canvas = null;
                    try {
                        canvas = surfaceHolder.lockCanvas(null);
                        if (canvas == null)
                            continue;
                        canvas.drawColor(Color.GREEN);
                    } finally {
                        if (canvas != null) {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        }
                    }
                }
            }
        }

    }

    public static class PointImageView extends androidx.appcompat.widget.AppCompatImageView {
        //public final int MaxSatCount = Integer.parseInt(Beacons.getText().toString());
        //public int MaxSatCount = 0;
        public ArrayList<Point> PointList = new ArrayList<Point>();
        private Point point;
        Bitmap bitmap;
        Bitmap bitmapAlpha;
        double [][] Gdop;
        boolean redrawGDOP;
        public void setGdop(double[][] gdop) {
            Gdop = gdop;
        }

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

        public void DrawGdop(Canvas canvas) {
            super.draw(canvas);
            for (int i = 0; i < Gdop.length; i++)
                for (int j = 0; j < Gdop[0].length; j++)
                {
                    Paint paint = new Paint();
                    if (Gdop[i][j] < 0.5) // 0.5
                    {
                        paint.setColor(Color.BLUE);
                    }
                    else if (Gdop[i][j] >=0.5  || Gdop[i][j] < 0.8) //0.5 0.8
                    {
                        paint.setColor(Color.YELLOW);
                    }
                    else
                    {
                        paint.setColor(Color.RED);
                    }
                    paint.setStrokeWidth(1f);

                    canvas.drawPoint(i, j, paint);
                    invalidate();
                }
           // redrawGDOP =false;
        }

        public void DrawBitMap(Canvas canvas)
        {
            super.draw(canvas);
           // Canvas canvas = new Canvas();

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

            int[] colors = new int[300*300];
            Arrays.fill(colors, 0, 300*100, Color.argb(85, 255, 0, 0));
            Arrays.fill(colors, 300*100, 300*200, Color.GREEN);
            Arrays.fill(colors, 300*200, 300*300, Color.BLUE);

            bitmap = Bitmap.createBitmap(colors, 300, 300, Bitmap.Config.RGB_565);
            bitmapAlpha = Bitmap.createBitmap(colors, 300, 300, Bitmap.Config.ARGB_8888);
            canvas.drawBitmap(bitmap, 50, 50, paint);
            canvas.drawBitmap(bitmapAlpha, 550, 50, paint);
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
        }

        @Override
        public void draw(Canvas canvas) {
            super.draw(canvas);
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(30f);
            if(Gdop !=null)
            {
              DrawGdop(canvas);
            }
          //  DrawBitMap(canvas);
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


