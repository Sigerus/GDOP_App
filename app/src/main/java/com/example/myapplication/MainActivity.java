package com.example.myapplication;


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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.Math.random;

public class MainActivity extends Activity implements View.OnTouchListener {
    TextView tv;
    TextView tv2;
    EditText Beacons;
    Switch Switch;
    Button Ok;
    Button Plus;
    Button Go;
    Button KO;
//////////////////////////////////////////////////
    Button Room;
    final Context context = this;
    public static boolean FlagRoom = false;
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
///////////////////////////////////////////////
    String Touch = "";
    String MoveTouch = "";
    public static int Key = 0;
    public static int Corners = 0;
    private ImageView drawingImageView;
    public static boolean Flag = true;
//    private CoordMap coordMap;


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
        Switch = findViewById(R.id.switch2);
        Plus = findViewById(R.id.button2);
        Go = findViewById(R.id.button3);
        Room = findViewById(R.id.button5);
        KO = findViewById(R.id.button4);


        //tv.setOnTouchListener(this);
        //setContentView(tv);
        roomImageView = (RoomImageView) findViewById(R.id.imageView);
        roomImageView.setOnTouchListener(this);



// плохой код. только для демонстрации
        // imageView.setImageDrawable(new DrawView(this));
        //drawingImageView = (ImageView) findViewById(R.id.imageView);
       // gdopImageView = (GDOPImageView) findViewById(R.id.imageView2);
        /*
        //////////////////////////   Сетка
        drawingImageView = (ImageView) findViewById(R.id.imageView);
        //Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        //Function(size);
        pointImageWidth = size.x;
        pointImageHeight = size.y;
        Bitmap bitmap = Bitmap.createBitmap((int) pointImageWidth, pointImageHeight , Bitmap.Config.ARGB_8888);
//        Bitmap bitmap = Bitmap.createBitmap(drawingImageView.getWidth(), drawingImageView.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawingImageView.setImageBitmap(bitmap);

        Paint paint = new Paint();
        paint.setColor(Color.DKGRAY);
        paint.setStrokeWidth(3);

//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        pointImageWidth = size.x;
//        pointImageHeight = size.y;



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
                } else {
                    Flag = true;
                }

            }
        });

        KO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlagRoom = true;
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

        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Beacons.getText().toString().equals("") || Beacons.getText().toString().equals("0")) {
                    tv.setText("Введите количество маяков");
                } else {
                    Key += Integer.parseInt(Beacons.getText().toString());
                    Beacons.setKeyListener(null);
                    v.setClickable(false);
                }
                FlagRoom = false;
            }
        });

        Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Key += 1;
                FlagRoom = false;
            }
        });

        Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), String.valueOf(roomImageView.getWidth()) + " " + String.valueOf(roomImageView.getHeight()), Toast.LENGTH_LONG).show();
                FlagRoom = false;

                double[][] Gdop;
                Log.d("Matrix calc started", String.valueOf(SystemClock.elapsedRealtimeNanos()));
                //создаем отдельный поток для расчета матрицы
                new CalcGDOP().execute();
                Log.d("Matrix calc finished", String.valueOf(SystemClock.elapsedRealtimeNanos()));

            }
        });
    }

 public class ReturnCorner
 {
     public int Return()
     {
         return Corners;
     }
 }

private class Room extends androidx.appcompat.widget.AppCompatImageView
{

    public Room(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void CreateBitMap(int a) {

        //Bitmap bitmap1 = Bitmap.createBitmap( roomImageView.getWidth(), roomImageView.getHeight(), Bitmap.Config.RGB_565);
    }

}


private class CalcGDOP extends AsyncTask<String, Void,double[][]> {
    /** The system calls this to perform work in a worker thread and
     * delivers it the parameters given to AsyncTask.execute() */
    protected double[][] doInBackground(String... method) {
//        if(Arrays.toString(method).equals("ToF")) {
        if(Flag) {
            ToF_Method ToF_method = new ToF_Method();
            Flag = true;
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        getX = (int) event.getX();
        getY = (int) event.getY();
        int CapturedPointIndex = 0;
        boolean Captured = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if (FlagRoom) {
                    if (roomImageView.CornerList.size() != Corners) {
                        Touch = "X: " + (getX - 2) + "\n" + "Y: " + (abs(getY - 1850)) + "\n";
                        roomImageView.CornerList.add(new Point(getX, getY));
                        roomImageView.invalidateImage();
                    } else {
                        Touch = "X: " + (getX/* - 2*/) + "\n" + "Y: " + (abs(getY - 1850)) + "\n";
                    }
                } else {
                    if (roomImageView.PointList.size() != Key) {
                        Touch = "X: " + (getX - 2) + "\n" + "Y: " + (abs(getY - 1850)) + "\n";
                        roomImageView.PointList.add(new Point(getX, getY));
                        roomImageView.invalidateImage();
                    } else {
                        Touch = "X: " + (getX/* - 2*/) + "\n" + "Y: " + (abs(getY - 1850)) + "\n";
                    }
                }
                tv.setText(Touch);
                break;
            }

            //остался баг с пожиранием маяков
            case MotionEvent.ACTION_MOVE: {
                if(FlagRoom == false) {
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

                        }
                        roomImageView.invalidateImage();
                    }
                } else {
                    for (int i = 0; i < roomImageView.CornerList.size(); i++) {
                        int offsetX = abs(getX - roomImageView.CornerList.get(i).x);
                        int offsetY = abs(getY - roomImageView.CornerList.get(i).y);
                        if (offsetX < 50 && offsetY < 50) {
                            Captured = true;
                            boolean Trace = false;
                            CapturedPointIndex = i;
                            if (getX < 0) {
                                roomImageView.CornerList.get(i).x = 0;
                            } else if (getX > roomImageView.getWidth()) {
                                roomImageView.CornerList.get(i).x = roomImageView.getWidth();
                                roomImageView.CornerList.get(i).y = getY;
                            } else if (getY < 0) {
                                roomImageView.CornerList.get(i).y = 0;
                            } else if (getY > roomImageView.getHeight()) {
                                roomImageView.CornerList.get(i).y = roomImageView.getHeight();
                                roomImageView.CornerList.get(i).x = getX;
                            } else {
                                roomImageView.CornerList.get(i).x = getX;
                                roomImageView.CornerList.get(i).y = getY;
                            }

                        }
                        roomImageView.invalidateImage();
                    }
                }
                MoveTouch = "X: " + (getX - 2) + "\n" + "Y: " + (abs(getY - 1850)) + "\n";
                tv.setText(MoveTouch);
                break;
            }
            case MotionEvent.ACTION_UP: {
                if (FlagRoom == false) {
                    if (Captured) {
                        roomImageView.PointList.get(CapturedPointIndex).x = getX;
                        roomImageView.PointList.get(CapturedPointIndex).y = getY;
                        roomImageView.invalidateImage();
                    }
                    break;
                } else {
                    if (Captured) {
                        roomImageView.CornerList.get(CapturedPointIndex).x = getX;
                        roomImageView.CornerList.get(CapturedPointIndex).y = getY;
                        roomImageView.invalidateImage();
                    }
                    break;
                }
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
        public ArrayList<Point> PointList = new ArrayList<Point>();
        public ArrayList<Point> CornerList = new ArrayList<Point>();
        private Point point;
        Bitmap GDOPbitmap;
        Bitmap bitmapAlpha;
        double[][] GDOP;
        boolean redrawGDOP;
        //ReturnCorner Corner = new ReturnCorner();

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
            paint.setStrokeWidth(1f);
            boolean indintersection = false;

            for (int k = 0; k < Corners-1; k++) {
                double ax1 = getWidth();
                double ay1 = getHeight();
                double ax2 = PointList.get(k).x;
                double ay2 = PointList.get(k).y;

                double bx1 = CornerList.get(k).x;
                double by1 = CornerList.get(k).y;
                double bx2 = CornerList.get(k + 1).x;
                double by2 = CornerList.get(k + 1).y;


                double v1 = (bx2 - bx1) * (ay1 - by1) - (by2 - by1) * (ax1 - bx1);
                double v2 = (bx2 - bx1) * (ay2 - by1) - (by2 - by1) * (ax2 - bx1);
                double v3 = (ax2 - ax1) * (by1 - ay1) - (ay2 - ay1) * (bx1 - ax1);
                double v4 = (ax2 - ax1) * (by2 - ay1) - (ay2 - ay1) * (bx2 - ax1);
                if ((v1 * v2 < 0) && (v3 * v4 < 0)) {
                    indintersection = true;
                }
            }


            for (int i = 0; i < GDOP.length; i++)
                for (int j = 0; j < GDOP[0].length; j++) {
                    if(indintersection = true)
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
                /*if(PointList.size() == Key)
                {
                    paint.setColor(Color.RED);
                    canvas.drawPoint(PointList.get(Key).x, PointList.get(Key).y, paint);
                    invalidate();
                }*/



                //углы
                Paint Corner = new Paint();
                Corner.setColor(Color.BLACK);
                Corner.setStrokeWidth(50f);
                for (Point p : CornerList) {
                    canvas.drawPoint(p.x, p.y, Corner);
                    invalidate();
                }
                //стены
                Paint RoomLine = new Paint();
                RoomLine.setColor(Color.BLACK);
                RoomLine.setStrokeWidth(40f);
                if (CornerList.size() > 1) {
                    int flag = 0;
                    for (int i = 0; i < CornerList.size() - 1; i++) {
                        canvas.drawLine(CornerList.get(i).x, CornerList.get(i).y, CornerList.get(i + 1).x, CornerList.get(i + 1).y, RoomLine);
                        flag = i;
                    }
                    if(CornerList.size() == Corners) {
                        canvas.drawLine(CornerList.get(flag + 1).x, CornerList.get(flag + 1).y, CornerList.get(0).x, CornerList.get(0).y, RoomLine);
                    }
                }

            /*Paint RoomLine = new Paint();
            RoomLine.setColor(Color.BLACK);
            RoomLine.setStrokeWidth(40f);
            Paint Corner = new Paint();
            Corner.setColor(Color.BLACK);

            if (CornerList.size() != 0) {
                for (Point p : CornerList) {
                    canvas.drawPoint(p.x, p.y, Corner);
                    invalidate();
                }
                if (CornerList.size() > 3) {
                    int flag = 0;
                for (int i = 0; i < CornerList.size() - 1; i++) {
                    canvas.drawLine(CornerList.get(i).x, CornerList.get(i).y, CornerList.get(i + 1).x, CornerList.get(i + 1).y, RoomLine);
                    flag = i;
                }
                canvas.drawLine(CornerList.get(flag).x, CornerList.get(flag).y, CornerList.get(0).x, CornerList.get(0).y, RoomLine);
            }
           }*/

        }
    }


}




