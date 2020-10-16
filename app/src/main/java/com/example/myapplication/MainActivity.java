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

import java.util.ArrayList;

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
    Button OXOY;
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
    public int pointImageHeight = 0;
    public int pointImageWidth = 0;
    public int ScreenStep = 100;
    public int gridValueX = 0;
    public int gridValueY = 0;
    public int gridcountX=8;
    public int gridcountY=18;
    ///////////////////////////////////////////////
    String Touch = "";
    String MoveTouch = "";
    public int Key = 0;
    public int Corners = 0;
    public ImageView drawingImageView;
    public boolean Flag = true;
//    private CoordMap coordMap;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        tv = new TextView(this);
        tv2 = new TextView(this);
        tv = findViewById(R.id.textView3);
        // tv2 = findViewById(R.id.textView);
        Beacons = findViewById(R.id.editText3);

        Ok = findViewById(R.id.button);
        Switch = findViewById(R.id.switch2);
        Plus = findViewById(R.id.button2);
        Go = findViewById(R.id.button3);
        OXOY = findViewById(R.id.room);
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

        OXOY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                LayoutInflater li = LayoutInflater.from(context);
                View roomdialogView = li.inflate(R.layout.roomdialog2, null);
                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);
                mDialogBuilder.setView(roomdialogView);
                //final EditText userInput = (EditText) roomdialogView.findViewById(R.id.input_text);
                final TextView inputX=(EditText) roomdialogView.findViewById((R.id.inputX));
                final TextView inputY=(EditText) roomdialogView.findViewById((R.id.inputY));


                mDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        //Corners = Integer.parseInt(userInput.getText().toString());
                                        gridValueX=Integer.parseInt(inputX.getText().toString());
                                        gridValueY=Integer.parseInt(inputY.getText().toString());
                                        pointImageWidth = roomImageView.getWidth();
                                        pointImageHeight = roomImageView.getHeight();
                                        roomImageView.valueX=gridValueX;
                                        roomImageView.valueY=gridValueY;
                                        roomImageView.height=pointImageHeight;
                                        roomImageView.width=pointImageWidth;

                                        arg0.setClickable(false);


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
                invalidateOptionsMenu();
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
                Log.d("Matrix calc finished", String.valueOf(SystemClock.elapsedRealtimeNanos()));

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

    public void CreateBitMap(double[][] GDOP) {

        Bitmap bitmap = Bitmap.createBitmap( roomImageView.getWidth(), roomImageView.getHeight(), Bitmap.Config.RGB_565);
        for (int i = 0; i < GDOP.length; i++)
            for (int j = 0; j < GDOP[0].length; j++) {
                if (GDOP[i][j] <= 1) {
                    bitmap.setPixel(i,j,getResources().getColor(R.color.colorGDOP1,getTheme()));
                } else if (GDOP[i][j] > 1f && GDOP[i][j] < 1.2f) {
                    bitmap.setPixel(i,j,getResources().getColor(R.color.colorGDOP15,getTheme()));
                } else if (GDOP[i][j] >= 1.2f && GDOP[i][j] < 2f) {
                    bitmap.setPixel(i,j,getResources().getColor(R.color.colorGDOP2,getTheme()));
                } else if (GDOP[i][j] >= 2f && GDOP[i][j] < 2.5f) {
                    bitmap.setPixel(i,j,getResources().getColor(R.color.colorGDOP25,getTheme()));
                } else if (GDOP[i][j] >= 2.5f && GDOP[i][j] < 3f) {
                    bitmap.setPixel(i,j,getResources().getColor(R.color.colorGDOP3,getTheme()));
                } else if (GDOP[i][j] >= 3f && GDOP[i][j] < 3.5f) {
                    bitmap.setPixel(i,j,getResources().getColor(R.color.colorGDOP35,getTheme()));
                } else if (GDOP[i][j] >= 3.5f && GDOP[i][j] < 4f) {
                    bitmap.setPixel(i,j,getResources().getColor(R.color.colorGDOP4,getTheme()));
                } else if (GDOP[i][j] >= 4f && GDOP[i][j] < 4.5f) {
                    bitmap.setPixel(i,j,getResources().getColor(R.color.colorGDOP45,getTheme()));
                } else if (GDOP[i][j] >= 4.5f) {
                    bitmap.setPixel(i,j,getResources().getColor(R.color.colorGDOP5,getTheme()));
                }
            }
        roomImageView.setGDOPbitmap(bitmap);
    }


    class Zoom {
    RoomImageView roomImageView;
    Bitmap bitmap = Bitmap.createBitmap(roomImageView.getWidth(), roomImageView.getHeight(),
            Bitmap.Config.ARGB_8888);


    }

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
        public ArrayList<Point> lines = new ArrayList<Point>();
        public int height;
        private Point point;
        Bitmap GDOPbitmap;
        Bitmap bitmapAlpha;
        double[][] GDOP;
        boolean redrawGDOP;
        public int width;
        public int ScreenStep = 100;
        public int gridcountX=8;
        private RoomImageView roomImageView;
        public int gridcountY=18;
        public int valueX;
        public int valueY;
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
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas)
            ;
        }

        @Override
        public void draw(Canvas canvas) {
            super.draw(canvas);
            Paint paint4 = new Paint();
            paint4.setColor(Color.BLACK);
            paint4.setStrokeWidth(30f);

            if (GDOP != null) {
                DrawBitMap(canvas);
            }
            if (point != null) {

            }
            for (Point p : PointList) {
                canvas.drawPoint(p.x, p.y, paint4);
                invalidate();
            }
            Paint RoomLine = new Paint();
            RoomLine.setColor(Color.BLACK);
            RoomLine.setStrokeWidth(40f);
            Paint Corner = new Paint();
            Corner.setColor(Color.BLACK);

            //Bitmap bitmap = Bitmap.createBitmap((int) width, height , Bitmap.Config.ARGB_8888);
           // final Canvas canvas = new Canvas(bitmap);
         //   drawingImageView.setImageBitmap(bitmap);

            final Paint paint = new Paint();
            paint.setColor(Color.DKGRAY);
            paint.setStrokeWidth(3);

            final Paint paint1 = new Paint();
            paint1.setColor(Color.BLACK);
            paint1.setStrokeWidth(8);


            Paint shadowPaint = new Paint();
            shadowPaint.setTextSize(40.0f);
            shadowPaint.setShadowLayer(00.0f, 0.0f, 0.0f, Color.BLACK);


            canvas.drawText(Integer.toString(0),50,1850,shadowPaint);
            canvas.drawLine(0,1800,width,1800,paint1 );//ох
            canvas.drawLine(100,0,100,height,paint1 );//оу

            for (int i = 0; i <= height-200; i += ScreenStep) { // Горизонтальные линии
                canvas.drawText(String.valueOf(valueY-valueY/gridcountY*i/ScreenStep), 5, i +110, shadowPaint);
                //canvas.drawText(String.valueOf(pointImageHeight - 50), 5, i + 10, shadowPaint);
            }

            for (int i = 0; i <= width-200; i += ScreenStep) { // Вертикальные линии
                canvas.drawText(String.valueOf(valueX-valueX/gridcountX*i/ScreenStep),870-i,1850,shadowPaint);
                //canvas.drawText(String.valueOf(pointImageWidth - 100),i+170,1850,shadowPaint);
            }

            for (int i = 0; i <= height; i += ScreenStep) { // Вертикальные линии
                canvas.drawLine(100, i, width, i, paint);
            }

            for (int i = 0; i <= width; i += ScreenStep) {// Горизонтальные линии
                canvas.drawLine(i, 0, i, 1800, paint);
            }
        }
    }

}

