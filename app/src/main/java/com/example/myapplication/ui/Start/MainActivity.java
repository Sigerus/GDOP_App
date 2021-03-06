package com.example.myapplication.ui.Start;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.abs;

public class MainActivity extends Activity implements View.OnTouchListener {
    TextView tv;
    TextView tv2;
    TextView tvY1;
    EditText Beacons;
    EditText EditSt;
    Switch Switch;
    Button Ok;
    Button Plus;
    Button Go;
    Button ButtonSt;

    //////////////////////////////////////////////////
    Button Room;
    final Context context = this;
    public boolean FlagRoom = false;
    /////////////////////////////////////////////////
    private int getX;
    private int getY;
    private RoomImageView roomImageView;
    //private CoordMap drawingImageView;
    //private GDOPImageView gdopImageView;
    private ToF_Method ToF_method;
    private TDoA_Method TDoA_method;
    ////////////////////////////////////////////////
    private int pointImageHeight = 0;
    private int pointImageWidth = 0;
    private int ScreenStep = 100;
    private int gridcountX=10;
    private int gridcountY=19;
    ///////////////////////////////////////////////
    String Touch = "";
    String MoveTouch = "";
    public int Key = 0;
    public int Corners = 0;
    private ImageView drawingImageView;
    public boolean Flag = true;
//    private CoordMap coordMap;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main2);
        tv = new TextView(this);
        tv2 = new TextView(this);
        tv = findViewById(R.id.textView3);
       // tv2 = findViewById(R.id.textView);
        Beacons = findViewById(R.id.editText3);
        EditSt = findViewById(R.id.editText);
        ButtonSt= findViewById(R.id.button4);
        Ok = findViewById(R.id.button);
        Switch = findViewById(R.id.switch2);
        Plus = findViewById(R.id.button2);
        Go = findViewById(R.id.button3);
        Room = findViewById(R.id.button5);
        //tv.setOnTouchListener(this);
        //setContentView(tv);

// плохой код. только для демонстрации
        // imageView.setImageDrawable(new DrawView(this));
        roomImageView = (RoomImageView) findViewById(R.id.imageView);
        //drawingImageView = (ImageView) findViewById(R.id.imageView);
        // gdopImageView = (GDOPImageView) findViewById(R.id.imageView2);
        roomImageView.setOnTouchListener(this);
        //////////////////////////
        drawingImageView = (ImageView) findViewById(R.id.imageView);
        //Display display = getWindowManager().getDefaultDisplay();



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
        Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Flag = false;
                }else{
                    Flag = true;
                }

            }
        });

        Room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                LayoutInflater li = LayoutInflater.from(context);
                View roomdialogView = li.inflate(R.layout.roomdialog, null);
                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);
                mDialogBuilder.setView(roomdialogView);
                final EditText userInput = (EditText) roomdialogView.findViewById(R.id.input_text);
                mDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //Вводим текст и отображаем в строке ввода на основном экране:
                                        //  final_text.setText(userInput.getText());
                                        Corners = Integer.parseInt(userInput.getText().toString());
                                        if(Corners > 2) {
                                            FlagRoom = true;
                                        }
                                    }
                                })
                        .setNegativeButton("Отмена",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = mDialogBuilder.create();

                //и отображаем его:
                alertDialog.show();

            }
        });

        ButtonSt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EditSt.getText().toString().equals("") || EditSt.getText().toString().equals("0")) {
                    tv.setText("Введите размеры сетки");
                } else {
                    int x,y;
                    x=(Integer.parseInt(EditSt.getText().toString()));
                    y=(Integer.parseInt(EditSt.getText().toString()));
                    Point size = new Point();
                    Function(size);
                    //pointImageWidth = 1200;

                    pointImageWidth = roomImageView.getWidth();
                    //pointImageHeight = size.y;
                    pointImageHeight = roomImageView.getHeight();
                    Bitmap bitmap = Bitmap.createBitmap((int) pointImageWidth, pointImageHeight , Bitmap.Config.ARGB_8888);
//        Bitmap bitmap = Bitmap.createBitmap(drawingImageView.getWidth(), drawingImageView.getHeight(),Bitmap.Config.ARGB_8888);
                    final Canvas canvas = new Canvas(bitmap);
                    drawingImageView.setImageBitmap(bitmap);

                    final Paint paint = new Paint();
                    paint.setColor(Color.DKGRAY);
                    paint.setStrokeWidth(3);
                    final Paint paint1 = new Paint();
                    paint1.setColor(Color.BLACK);
                    paint1.setStrokeWidth(8);
                    final Paint paint2= new Paint();
                    paint2.setColor(Color.BLACK);
                    paint2.setStrokeWidth(50);
                    //tvY1.setText(Integer.parseInt());
                    //roomImageView.getHeight();

                    int k;
                    int p;
                    Paint shadowPaint = new Paint();
                    //   shadowPaint.setAntiAlias(true);
                    // shadowPaint.setColor(Color.WHITE);
                    shadowPaint.setTextSize(40.0f);
                    //  shadowPaint.setStrokeWidth(20.0f);
                    //  shadowPaint.setStyle(Paint.Style.STROKE);
                    shadowPaint.setShadowLayer(00.0f, 0.0f, 0.0f, Color.BLACK);
                    String y2;
                    String y3;
                    //   canvas.drawText(y, 100, 200, shadowPaint);

                    //  canvas.drawText(y,200,200,paint2);

                    for (int i = 0; i <= pointImageHeight-100; i += ScreenStep) { // Горизонтальные линии
                        //  k=Integer.parseInt(y);

                        // p=(k+i);
                        //  y3=Integer.toString(p);
                        //  y2=Integer.toString(Integer.parseInt(y)+1800-i);

                        // canvas.drawText(y2,100,200,100,i,shadowPaint);
                        canvas.drawText(String.valueOf(gridcountY*y-(y+y*i/ScreenStep)), 0, i, shadowPaint);
                        //  canvas.drawText(y3,i+100,2200,shadowPaint);
                    }
                    for (int i = 0; i <= pointImageHeight; i += ScreenStep) {
                        //  k=Integer.parseInt(y);
                        //  p=(k+i);
                        // y3=Integer.toString(p);
                        canvas.drawText(String.valueOf(x+x*i/ScreenStep),i+200,1850,shadowPaint);

                    }
                    canvas.drawText(Integer.toString(0),50,1850,shadowPaint);
                    canvas.drawLine(0,1800,pointImageWidth,1800,paint1 );//ох
                    canvas.drawLine(100,0,100,pointImageHeight,paint1 );//оу

                    //  canvas.drawLine(100,0,130,70,paint1 );//оу правая стрелка
                    // canvas.drawLine(100,0,70,70,paint1 );//оу левая стрелка
//
                    //  canvas.drawLine(pointImageWidth,2100,pointImageWidth - 70,2070,paint1 );//ох верхняя стрелка
                    // canvas.drawLine(pointImageWidth,2100,pointImageWidth - 70,2130,paint1 );//ох верхняя стрелка

                    for (int i = 0; i <= pointImageHeight; i += ScreenStep) // Вертикальные линии
                        canvas.drawLine(0, i, pointImageWidth, i, paint);
                    for (int i = 0; i <= pointImageWidth; i += ScreenStep) // Горизонтальные линии
                        canvas.drawLine(i, 0, i, pointImageHeight, paint);
                    v.setClickable(false);

                }
            }
        });


        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Beacons.getText().toString().equals("") || Beacons.getText().toString().equals("0")) {
                    tv.setText("Введите количество маяков");
                } else {
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
                Toast.makeText(getApplicationContext(), String.valueOf(roomImageView.getWidth()) + " " + String.valueOf(roomImageView.getHeight()), Toast.LENGTH_LONG).show();


                double[][] Gdop;
                //MatrixMath matrixMath = new MatrixMath();
                Log.d("Matrix calc started", String.valueOf(SystemClock.elapsedRealtimeNanos()));
                //создаем отдельный поток для расчета матрицы
                new CalcGDOP().execute();
                //Gdop = matrixMath.main(roomImageView.PointList, roomImageView.getWidth(), roomImageView.getHeight());
                //gdopImageView.setGDOP(Gdop);
                Log.d("Matrix calc finished", String.valueOf(SystemClock.elapsedRealtimeNanos()));
                //roomImageView.redrawGDOP = true;

                //roomImageView.setGDOP(Gdop);
                //CreateBitMap(Gdop);

                //gdopImageView.invalidateImage();
                //roomImageView.invalidateImage();
                // pointImageView.DrawGdop(math.main(pointImageView.PointList));
                // pointImageView.DrawBitMap();
                //pointImageView.DrawGdop(Gdop);
                //PointImageView.DrawGdop(Gdop);
                //pointImageView.invalidateImage();
                //math.main(pointImageView.PointList);

            }
        });
    }
    private class CalcGDOP extends AsyncTask<String, Void,double[][]> {
        /** The system calls this to perform work in a worker thread and
         * delivers it the parameters given to AsyncTask.execute() */
        protected double[][] doInBackground(String... method) {
          //  if(Arrays.toString(method).equals("ToF")) {
                if(Flag) {
                ToF_Method ToF_method = new ToF_Method();
                Flag=true;
                return ToF_method.main(roomImageView.PointList, roomImageView.getWidth(), roomImageView.getHeight());
            } else {
                TDoA_Method TDoA_method = new TDoA_Method();
                return TDoA_method.main(roomImageView.PointList, roomImageView.getWidth(), roomImageView.getHeight());
            }
        }

        /** The system calls this to perform work in the UI thread and delivers
         * the result from doInBackground() */
        protected void onPostExecute(double[][] result) {
            roomImageView.setGDOP(result);
            CreateBitMap(result);
        }
    }
    public Point Function(Point size)
    {
        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
        display.getSize(size);
        return size;
    }
    public void CreateBitMap(double[][] GDOP) {

        // Canvas canvas = new Canvas();
        //int[] colors = new int[roomImageView.getWidth() * roomImageView.getHeight()];
        //Bitmap bitmap = Bitmap.createBitmap(colors, 300, 300, Bitmap.Config.RGB_565);
        Bitmap bitmap = Bitmap.createBitmap( roomImageView.getWidth(), roomImageView.getHeight(), Bitmap.Config.RGB_565);
        //Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        for (int i = 0; i < GDOP.length; i++)
            for (int j = 0; j < GDOP[0].length; j++) {
                // Paint paint = new Paint();
                if (GDOP[i][j] <= 1) {
                    bitmap.setPixel(i,j,getResources().getColor(R.color.colorGDOP1,getTheme()));
                    //paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP1));
                    //canvas.drawCircle(i, j,1, paint);
                    //canvas.drawPoint(i, j, paint);
                    //  invalidate();
                    //  break;
                } else if (GDOP[i][j] > 1f && GDOP[i][j] < 1.2f) {
                    bitmap.setPixel(i,j,getResources().getColor(R.color.colorGDOP15,getTheme()));
                    //paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP15));
                    //canvas.drawCircle(i, j,1, paint);
                    //canvas.drawPoint(i, j, paint);
                    //canvas.drawPoint(i, j, paintYELLOW);
                    //  invalidate();
                    //  break;
                } else if (GDOP[i][j] >= 1.2f && GDOP[i][j] < 2f) {
                    bitmap.setPixel(i,j,getResources().getColor(R.color.colorGDOP2,getTheme()));
                    //paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP2));
                    //canvas.drawCircle(i, j,1, paint);
                    //canvas.drawPoint(i, j, paint);
                    //canvas.drawPoint(i, j, paintYELLOW);
                    //   invalidate();
                    //  break;
                } else if (GDOP[i][j] >= 2f && GDOP[i][j] < 2.5f) {
                    bitmap.setPixel(i,j,getResources().getColor(R.color.colorGDOP25,getTheme()));
                    // paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP25));
                    //canvas.drawPoint(i, j, paint);
                    // canvas.drawPoint(i, j, paint);
                    //  invalidate();
                    //   break;
                } else if (GDOP[i][j] >= 2.5f && GDOP[i][j] < 3f) {
                    bitmap.setPixel(i,j,getResources().getColor(R.color.colorGDOP3,getTheme()));
                    // paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP3));
                    //canvas.drawCircle(i, j,1, paint);
                    // canvas.drawPoint(i, j, paint);
                    //canvas.drawPoint(i, j, paintRED);
                    //  invalidate();
                    //  break;
                } else if (GDOP[i][j] >= 3f && GDOP[i][j] < 3.5f) {
                    bitmap.setPixel(i,j,getResources().getColor(R.color.colorGDOP35,getTheme()));
                    //paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP35));
                    //canvas.drawCircle(i, j,1, paint);
                    //canvas.drawPoint(i, j, paint);
                    //canvas.drawPoint(i, j, paintRED);
                    //   invalidate();
                    // break;
                } else if (GDOP[i][j] >= 3.5f && GDOP[i][j] < 4f) {
                    bitmap.setPixel(i,j,getResources().getColor(R.color.colorGDOP4,getTheme()));
                    //paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP4));
                    //canvas.drawPoint(i, j, paint);
                    //canvas.drawPoint(i, j, paintRED);
                    //   invalidate();
                    // break;
                } else if (GDOP[i][j] >= 4f && GDOP[i][j] < 4.5f) {
                    bitmap.setPixel(i,j,getResources().getColor(R.color.colorGDOP45,getTheme()));
                    //paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP45));
                    //canvas.drawCircle(i, j,1, paint);
                    //canvas.drawPoint(i, j, paint);
                    //canvas.drawPoint(i, j, paintRED);
                    //   invalidate();
                    //  break;
                } else if (GDOP[i][j] >= 4.5f) {
                    bitmap.setPixel(i,j,getResources().getColor(R.color.colorGDOP5,getTheme()));
                    //paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP5));
                    //canvas.drawCircle(i, j,1, paint);
                    //canvas.drawPoint(i, j, paint);
                    //canvas.drawPoint(i, j, paintRED);
                    //    invalidate();
                    // break;
                }
            }
        roomImageView.setGDOPbitmap(bitmap);
           /* int[] colors = new int[300 * 300];
            Arrays.fill(colors, 0, 300 * 100, Color.argb(85, 255, 0, 0));
            Arrays.fill(colors, 300 * 100, 300 * 200, Color.GREEN);
            Arrays.fill(colors, 300 * 200, 300 * 300, Color.BLUE);

            bitmap = Bitmap.createBitmap(colors, 300, 300, Bitmap.Config.RGB_565);
            bitmapAlpha = Bitmap.createBitmap(colors, 300, 300, Bitmap.Config.ARGB_8888);
            canvas.drawBitmap(bitmap, 50, 50, paint);
            canvas.drawBitmap(bitmapAlpha, 550, 50, paint);
            invalidate();*/
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
                if (roomImageView.PointList.size() != Key) {
                    Touch = "X: " + (getX - 2) + "\n" + "Y: " + (abs(getY - 1850)) + "\n";
                    roomImageView.PointList.add(new Point(getX, getY));
                    roomImageView.invalidateImage();
                } else {
                    Touch = "X: " + (getX/* - 2*/) + "\n" + "Y: " + (abs(getY - 1850)) + "\n";
                }
                //  pointImageView.setPoint(new Point(x, y));
                //tv.setText(null);
                //tv.setText("");
                tv.setText(Touch);
                //Touch = "";
                /*if(FlagRoom) {
                    if (roomImageView.CornerList.size() != Corners) {
                        roomImageView.CornerList.add(new Point(getX, getY));
                        roomImageView.invalidateImage();
                    }
                }*/
                break;
            }

            //остался баг с пожиранием маяков
            case MotionEvent.ACTION_MOVE: {
                for (int i = 0; i < roomImageView.PointList.size(); i++) {
                    int offsetX = abs(getX - roomImageView.PointList.get(i).x);
                    int offsetY = abs(getY - roomImageView.PointList.get(i).y);
                    if (offsetX < 50 && offsetY < 50) {
                        Captured = true;
                        boolean Trace = false;
                        CapturedPointIndex = i;
                        if (getX < 0) {
                            roomImageView.PointList.get(i).x = 0;
                        } else if (getX > roomImageView.getWidth()) {
                            roomImageView.PointList.get(i).x = roomImageView.getWidth();
                            roomImageView.PointList.get(i).y = getY;
                        } else if (getY < 0) {
                            roomImageView.PointList.get(i).y = 0;
                        } else if (getY > roomImageView.getHeight()) {
                            roomImageView.PointList.get(i).y = roomImageView.getHeight();
                            roomImageView.PointList.get(i).x = getX;
                        } else {
                            roomImageView.PointList.get(i).x = getX;
                            roomImageView.PointList.get(i).y = getY;
                        }
                        //for (int j = 0; j < roomImageView.PointList.size(); j++)
                        //{

                        //}
                        /*for (int j = 0; j < roomImageView.PointList.size(); j++)
                        {
                            if(getX == roomImageView.PointList.get(j).x && getY == roomImageView.PointList.get(j).y){
                                break;
                            }
                        }*/
                    }
                    roomImageView.invalidateImage();
                }
                MoveTouch = "X: " + (getX - 2) + "\n" + "Y: " + (abs(getY - 1850)) + "\n";
                tv.setText(MoveTouch);
                //Toast.makeText(this,"ACTION MOVE",Toast.LENGTH_SHORT).show();
                break;
            }
            case MotionEvent.ACTION_UP: {
                if (Captured) {
                    roomImageView.PointList.get(CapturedPointIndex).x = getX;
                    roomImageView.PointList.get(CapturedPointIndex).y = getY;
                    roomImageView.invalidateImage();
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



    public static class RoomImageView extends androidx.appcompat.widget.AppCompatImageView {
        //public final int MaxSatCount = Integer.parseInt(Beacons.getText().toString());
        //public int MaxSatCount = 0;
        public ArrayList<Point> PointList = new ArrayList<Point>();
        public ArrayList<Point> CornerList = new ArrayList<Point>();
        private Point point;
        Bitmap GDOPbitmap;
        Bitmap bitmapAlpha;
        double[][] GDOP;
        boolean redrawGDOP;

        public void setGDOPbitmap(Bitmap GDOPbitmap) {
            this.GDOPbitmap = GDOPbitmap;
        }

        public void setGDOP(double[][] GDOP) {
            this.GDOP = GDOP;
        }

        public RoomImageView(Context context) {
            super(context);
        }

        public RoomImageView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public RoomImageView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        public void invalidateImage() {
            invalidate();
        }

        public void DrawGDOP(Canvas canvas) {
            super.draw(canvas);
            Log.d("Render started", String.valueOf(SystemClock.elapsedRealtimeNanos()));
            Paint paint = new Paint();

           /* Paint paintBLUE = new Paint();
            paintBLUE.setColor(Color.rgb(0,0,255));
            paintBLUE.setStrokeWidth(1f);

            Paint paintYELLOW = new Paint();
            paintYELLOW.setColor(Color.YELLOW);
            paintYELLOW.setStrokeWidth(1f);

            Paint paintRED = new Paint();
            paintRED.setColor(Color.RED);
            paintRED.setStrokeWidth(1f);*/

            paint.setStrokeWidth(1f);

            for (int i = 0; i < GDOP.length; i++)
                for (int j = 0; j < GDOP[0].length; j++) {
                    // Paint paint = new Paint();
                    if (GDOP[i][j] <= 1) {
                        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP1));
                        //canvas.drawCircle(i, j,1, paint);
                        canvas.drawPoint(i, j, paint);
                        //  invalidate();
                        //  break;
                    } else if (GDOP[i][j] > 1f && GDOP[i][j] < 1.2f) {
                        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP15));
                        //canvas.drawCircle(i, j,1, paint);
                        canvas.drawPoint(i, j, paint);
                        //canvas.drawPoint(i, j, paintYELLOW);
                        //  invalidate();
                        //  break;
                    } else if (GDOP[i][j] >= 1.2f && GDOP[i][j] < 2f) {
                        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP2));
                        //canvas.drawCircle(i, j,1, paint);
                        canvas.drawPoint(i, j, paint);
                        //canvas.drawPoint(i, j, paintYELLOW);
                        //   invalidate();
                        //  break;
                    } else if (GDOP[i][j] >= 2f && GDOP[i][j] < 2.5f) {
                        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP25));
                        //canvas.drawPoint(i, j, paint);
                        canvas.drawPoint(i, j, paint);
                        //  invalidate();
                        //   break;
                    } else if (GDOP[i][j] >= 2.5f && GDOP[i][j] < 3f) {
                        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP3));
                        //canvas.drawCircle(i, j,1, paint);
                        canvas.drawPoint(i, j, paint);
                        //canvas.drawPoint(i, j, paintRED);
                        //  invalidate();
                        //  break;
                    } else if (GDOP[i][j] >= 3f && GDOP[i][j] < 3.5f) {
                        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP35));
                        //canvas.drawCircle(i, j,1, paint);
                        canvas.drawPoint(i, j, paint);
                        //canvas.drawPoint(i, j, paintRED);
                        //   invalidate();
                        // break;
                    } else if (GDOP[i][j] >= 3.5f && GDOP[i][j] < 4f) {
                        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP4));
                        canvas.drawPoint(i, j, paint);
                        //canvas.drawPoint(i, j, paintRED);
                        //   invalidate();
                        // break;
                    } else if (GDOP[i][j] >= 4f && GDOP[i][j] < 4.5f) {
                        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP45));
                        //canvas.drawCircle(i, j,1, paint);
                        canvas.drawPoint(i, j, paint);
                        //canvas.drawPoint(i, j, paintRED);
                        //   invalidate();
                        //  break;
                    }else if (GDOP[i][j] >= 4.5f) {
                        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP5));
                        //canvas.drawCircle(i, j,1, paint);
                        canvas.drawPoint(i, j, paint);
                        //canvas.drawPoint(i, j, paintRED);
                        //    invalidate();
                        // break;
                    }

                    //  paint.setStrokeWidth(1f);
                }
            //
            // redrawGDOP = false;
            invalidate();
            Log.d("Render finished", String.valueOf(SystemClock.elapsedRealtimeNanos()));
        }


        public void DrawBitMap(Canvas canvas) {
            super.draw(canvas);

            this.setImageBitmap(GDOPbitmap);
          /*   // Canvas canvas = new Canvas();
           // int[] colors = new int[roomImageView.getWidth() * 300];
          //  bitmap = Bitmap.createBitmap(colors, 300, 300, Bitmap.Config.RGB_565);
           Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            for (int i = 0; i < GDOP.length; i++)
                for (int j = 0; j < GDOP[0].length; j++) {
                    // Paint paint = new Paint();
                    if (GDOP[i][j] <= 1) {
                        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP1));
                        //canvas.drawCircle(i, j,1, paint);
                        canvas.drawPoint(i, j, paint);
                        //  invalidate();
                        //  break;
                    } else if (GDOP[i][j] > 1f && GDOP[i][j] < 1.2f) {
                        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP15));
                        //canvas.drawCircle(i, j,1, paint);
                        canvas.drawPoint(i, j, paint);
                        //canvas.drawPoint(i, j, paintYELLOW);
                        //  invalidate();
                        //  break;
                    } else if (GDOP[i][j] >= 1.2f && GDOP[i][j] < 2f) {
                        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP2));
                        //canvas.drawCircle(i, j,1, paint);
                        canvas.drawPoint(i, j, paint);
                        //canvas.drawPoint(i, j, paintYELLOW);
                        //   invalidate();
                        //  break;
                    } else if (GDOP[i][j] >= 2f && GDOP[i][j] < 2.5f) {
                        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP25));
                        //canvas.drawPoint(i, j, paint);
                        canvas.drawPoint(i, j, paint);
                        //  invalidate();
                        //   break;
                    } else if (GDOP[i][j] >= 2.5f && GDOP[i][j] < 3f) {
                        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP3));
                        //canvas.drawCircle(i, j,1, paint);
                        canvas.drawPoint(i, j, paint);
                        //canvas.drawPoint(i, j, paintRED);
                        //  invalidate();
                        //  break;
                    } else if (GDOP[i][j] >= 3f && GDOP[i][j] < 3.5f) {
                        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP35));
                        //canvas.drawCircle(i, j,1, paint);
                        canvas.drawPoint(i, j, paint);
                        //canvas.drawPoint(i, j, paintRED);
                        //   invalidate();
                        // break;
                    } else if (GDOP[i][j] >= 3.5f && GDOP[i][j] < 4f) {
                        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP4));
                        canvas.drawPoint(i, j, paint);
                        //canvas.drawPoint(i, j, paintRED);
                        //   invalidate();
                        // break;
                    } else if (GDOP[i][j] >= 4f && GDOP[i][j] < 4.5f) {
                        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP45));
                        //canvas.drawCircle(i, j,1, paint);
                        canvas.drawPoint(i, j, paint);
                        //canvas.drawPoint(i, j, paintRED);
                        //   invalidate();
                        //  break;
                    } else if (GDOP[i][j] >= 4.5f) {
                        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP5));
                        //canvas.drawCircle(i, j,1, paint);
                        canvas.drawPoint(i, j, paint);
                        //canvas.drawPoint(i, j, paintRED);
                        //    invalidate();
                        // break;
                    }
                }
            int[] colors = new int[300 * 300];
            Arrays.fill(colors, 0, 300 * 100, Color.argb(85, 255, 0, 0));
            Arrays.fill(colors, 300 * 100, 300 * 200, Color.GREEN);
            Arrays.fill(colors, 300 * 200, 300 * 300, Color.BLUE);

            bitmap = Bitmap.createBitmap(colors, 300, 300, Bitmap.Config.RGB_565);
            bitmapAlpha = Bitmap.createBitmap(colors, 300, 300, Bitmap.Config.ARGB_8888);
            canvas.drawBitmap(bitmap, 50, 50, paint);
            canvas.drawBitmap(bitmapAlpha, 550, 50, paint);
            invalidate();*/
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

            if (GDOP != null) {
                // DrawGDOP(canvas);
                DrawBitMap(canvas);
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
            Paint RoomLine = new Paint();
            RoomLine.setColor(Color.BLACK);
            RoomLine.setStrokeWidth(40f);
            Paint Corner = new Paint();
            Corner.setColor(Color.BLACK);

            /*if (CornerList.size() != 0) {
                for (Point p : CornerList) {
                    canvas.drawPoint(p.x, p.y, Corner);
                    invalidate();
                }
                if (CornerList.size() > 2) {
                    int flag = 0;
                for (int i = 0; i < CornerList.size(); i++) {
                    canvas.drawLine(CornerList.get(i).x, CornerList.get(i).y, CornerList.get(i + 1).x, CornerList.get(i + 1).y, RoomLine);
                    flag = i;
                }
                canvas.drawLine(CornerList.get(flag).x, CornerList.get(flag).y, CornerList.get(0).x, CornerList.get(0).y, RoomLine);
            }
            }*/

        }
    }

    /*    public static class GDOPImageView extends androidx.appcompat.widget.AppCompatImageView {
            //public final int MaxSatCount = Integer.parseInt(Beacons.getText().toString());
            //public int MaxSatCount = 0;
            public ArrayList<Point> PointList = new ArrayList<Point>();
            private Point point;
            Bitmap bitmap;
            Bitmap bitmapAlpha;
            double[][] GDOP;
            boolean redrawGDOP;

            public void setGDOP(double[][] GDOP) {
                this.GDOP = GDOP;
            }

            public GDOPImageView(Context context) {
                super(context);
            }

            public GDOPImageView(Context context, AttributeSet attrs) {
                super(context, attrs);
            }

            public GDOPImageView(Context context, AttributeSet attrs, int defStyle) {
                super(context, attrs, defStyle);
            }


            public void invalidateImage() {
                invalidate();
            }

            public void DrawGDOP(Canvas canvas) {
                super.draw(canvas);
                Paint paint = new Paint();
           /* Paint paintBLUE = new Paint();
            paintBLUE.setColor(Color.rgb(0,0,255));
            paintBLUE.setStrokeWidth(1f);

            Paint paintYELLOW = new Paint();
            paintYELLOW.setColor(Color.YELLOW);
            paintYELLOW.setStrokeWidth(1f);

            Paint paintRED = new Paint();
            paintRED.setColor(Color.RED);
            paintRED.setStrokeWidth(1f);
                paint.setStrokeWidth(1f);

                for (int i = 0; i < GDOP.length; i++)
                    for (int j = 0; j < GDOP[0].length; j++) {
                        // Paint paint = new Paint();
                        if (GDOP[i][j] <= 1) {
                            paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP1));
                            //canvas.drawCircle(i, j,1, paint);
                            canvas.drawPoint(i, j, paint);

                             invalidate();

                        } else if (GDOP[i][j] > 1f && GDOP[i][j] < 1.2f) {
                            paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP15));
                            //canvas.drawCircle(i, j,1, paint);
                            canvas.drawPoint(i, j, paint);
                            //canvas.drawPoint(i, j, paintYELLOW);
                            //  invalidate();
                        } else if (GDOP[i][j] >= 1.2f && GDOP[i][j] < 2f) {
                            paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP2));
                            //canvas.drawCircle(i, j,1, paint);
                            canvas.drawPoint(i, j, paint);
                            //canvas.drawPoint(i, j, paintYELLOW);
                              invalidate();
                        } else if (GDOP[i][j] >= 2f && GDOP[i][j] < 2.5f) {
                            paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP25));
                            //canvas.drawPoint(i, j, paint);
                            canvas.drawPoint(i, j, paint);
                             invalidate();
                        } else if (GDOP[i][j] >= 2.5f && GDOP[i][j] < 3f) {
                            paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP3));
                            //canvas.drawCircle(i, j,1, paint);
                            canvas.drawPoint(i, j, paint);
                            //canvas.drawPoint(i, j, paintRED);
                             invalidate();
                        } else if (GDOP[i][j] >= 3f && GDOP[i][j] < 3.5f) {
                            paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP35));
                            //canvas.drawCircle(i, j,1, paint);
                            canvas.drawPoint(i, j, paint);
                            //canvas.drawPoint(i, j, paintRED);
                             invalidate();
                        } else if (GDOP[i][j] >= 3.5f && GDOP[i][j] < 4f) {
                            paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP4));
                            canvas.drawPoint(i, j, paint);
                            //canvas.drawPoint(i, j, paintRED);
                             invalidate();
                        } else if (GDOP[i][j] >= 4f && GDOP[i][j] < 4.5f) {
                            paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP45));
                            //canvas.drawCircle(i, j,1, paint);
                            canvas.drawPoint(i, j, paint);
                            //canvas.drawPoint(i, j, paintRED);
                             invalidate();
                        }else if (GDOP[i][j] >= 4.5f) {
                            paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGDOP5));
                            //canvas.drawCircle(i, j,1, paint);
                            canvas.drawPoint(i, j, paint);
                            //canvas.drawPoint(i, j, paintRED);
                            invalidate();
                        }
                        //  paint.setStrokeWidth(1f);
                        invalidateImage();
                    }
                // redrawGDOP =false;
                // invalidate();
            }

            public void DrawBitMap(Canvas canvas) {
                super.draw(canvas);
                // Canvas canvas = new Canvas();

                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

                int[] colors = new int[300 * 300];
                Arrays.fill(colors, 0, 300 * 100, Color.argb(85, 255, 0, 0));
                Arrays.fill(colors, 300 * 100, 300 * 200, Color.GREEN);
                Arrays.fill(colors, 300 * 200, 300 * 300, Color.BLUE);

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
             //   Paint paint = new Paint();
             //   paint.setColor(Color.BLACK);
             //   paint.setStrokeWidth(30f);
                if (GDOP != null ) {
                    DrawGDOP(canvas);
                }
                /* //  DrawBitMap(canvas);
              Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setStrokeWidth(30f); //Вот оно
                canvas.drawPoint(point.x, point.y, paint);
                for (Point p : PointList)
                {
                    canvas.drawPoint(p.x, p.y, paint);
                    invalidate();
                }
                }
                for (Point p : PointList) {
                    canvas.drawPoint(p.x, p.y, paint);
                    invalidate();
                }
            }
        }*/

}

