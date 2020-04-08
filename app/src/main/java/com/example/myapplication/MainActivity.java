package com.example.myapplication;
import android.os.Environment;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import static java.lang.Math.abs;



public class MainActivity extends Activity implements View.OnTouchListener {
    private ImageView imageView;
    private final int Pick_image = 1;

    TextView inputText;
    TextView response;
    Button saveButton,readButton;

    private String filename = "SampleFile.txt";
    private String filepath = "MyFileStorage";
    File myExternalFile;
    File myExternalFile2;
    String myData = "";
    private String filename2 = "SampleFile2.txt";
    private String filepath2 = "MyFileStorage";

    int[] masX;
    int[] masY;


    final String DIR_SD = "MyFiles";
    final String FILENAME_SD = "fileSD";


    TextView tv;
    //TextView tv2;
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
    String[] koord ;
    String MoveTouch = "";
    public int Key = 0;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = new TextView(this);
        // tv2 = new TextView(this);
        tv = findViewById(R.id.textView3);
        //  tv2 = findViewById(R.id.textView);
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
                tv.setText("Какая-нибудь хуйня");
              //  Math math = new Math();
                double[][] Gdop = new double[100][100];
               // Gdop = math.main(pointImageView.PointList);
                //math.main(pointImageView.PointList);

            }
        });
        //Связываемся с нашим ImageView:
        imageView = (ImageView)findViewById(R.id.imageView);

        //Связываемся с нашей кнопкой Button:
        Button PickImage = (Button) findViewById(R.id.button6);
        //Настраиваем для нее обработчик нажатий OnClickListener:
        PickImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //Вызываем стандартную галерею для выбора изображения с помощью Intent.ACTION_PICK:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                //Тип получаемых объектов - image:
                photoPickerIntent.setType("image/*");
                //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
                startActivityForResult(photoPickerIntent, Pick_image);
            }
        });

        inputText = (TextView) findViewById(R.id.textView3);
        // response = (TextView) findViewById(R.id.response);


        saveButton =
                (Button) findViewById(R.id.saveExternalStorage);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] massX=new String[ pointImageView.PointList.size()];
                String[] massY=new String[ pointImageView.PointList.size()];
                //String n="/n";

                try {
                    FileOutputStream fos = new FileOutputStream(myExternalFile);
                  //  FileOutputStream fos2 = new FileOutputStream(myExternalFile2);
                    for (int i = 0; i < pointImageView.PointList.size(); i++) {
                        massX[i] = Integer.toString(pointImageView.PointList.get(i).x);
                        massY[i] = Integer.toString(pointImageView.PointList.get(i).y);
                        fos.write((massX[i]+" " + massY[i]+ "\n").getBytes() );
                        //fos.write(massY[i].getBytes());
                       // fos2.write((massY[i]+"\n").getBytes());
                    }
                    fos.close();
                  //  fos2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //  inputText.setText("");
//                response.setText("SampleFile.txt saved to External Storage...");
            }
        });

        readButton = (Button) findViewById(R.id.getExternalStorage);
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                masX=new int[pointImageView.PointList.size()];
                masY=new int[pointImageView.PointList.size()];



                try {
                    FileInputStream fin = new FileInputStream(myExternalFile);

                    byte[] bytes = new byte[fin.available()];
                    fin.read(bytes);
                    String Alltext = new String (bytes);
                    String[] ParsedText = Alltext.split("\n");
                    for(int i =0;i<ParsedText.length;i++)
                    {
                        pointImageView.PointList.get(i).x=Integer.parseInt(ParsedText[i].split(" ")[0]);
                        pointImageView.PointList.get(i).y=Integer.parseInt(ParsedText[i].split(" ")[1]);
                    }
                }
          /*      for(int i=0;i<pointImageView.PointList.size();i++){
                     = masX[i];
                     = masY[i];
                }*/
                catch(IOException ex) {

                  //  Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                pointImageView.invalidateImage();
  /*              try {
                    FileInputStream fis = new FileInputStream(myExternalFile);
                    DataInputStream in = new DataInputStream(fis);
                    BufferedReader br =
                            new BufferedReader(new InputStreamReader(in));
                    FileInputStream fis2 = new FileInputStream(myExternalFile2);
                    DataInputStream in2 = new DataInputStream(fis2);
                    BufferedReader br2 =
                            new BufferedReader(new InputStreamReader(in2));

                    String strLine;
                    ArrayList<String> list= new ArrayList<>(pointImageView.PointList.size());

                    while ((strLine = br.readLine()) != null) {

                        list.add(strLine);

                    }

                    in.close();
                    String[] stringArr= list.toArray(new String[pointImageView.PointList.size()]);



                    String strLine2;
                    ArrayList<String> list1=new ArrayList<>(pointImageView.PointList.size());

                    while ((strLine2 = br2.readLine()) != null) {
                        list1.add(strLine2);
                        // strLine2=br2.readLine();
                        //      getY=Integer.parseInt(strLine2);
                        //     getY=pointImageView.PointList.get(i).y;
                        //      pointImageView.PointList.add(new Point(getX, getY));
                        //          // getY = pointImageView.PointList.get(i).y;
                        // masY[i]=Integer.parseInt(strLine2);

                    }
                    in2.close();
                    String[] stringArr1= list1.toArray(new String[pointImageView.PointList.size()]);

                    for (int i = 0; i < pointImageView.PointList.size(); i++) {

                        masX[i] = Integer.parseInt(stringArr[i]);
                        masY[i] = Integer.parseInt(stringArr1[i]);

                    }
                    // pointImageView.invalidateImage();
                    //}



                } catch (IOException e) {
                    e.printStackTrace();
                }*/


                //inputText.setText(myData);
                //response.setText("SampleFile.txt data retrieved from Internal Storage...");
            }
        });

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            saveButton.setEnabled(false);
        }
        else {
            myExternalFile = new File(getExternalFilesDir(filepath), filename);
            myExternalFile2 = new File(getExternalFilesDir(filepath2), filename2);
        }


    }


    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
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

        public void DrawGdop(double[][] Gdop) {
            for (int i = 0; i < Gdop.length; i++)
                for (int j = 0; j < Gdop[0].length; i++)
                {
                    Paint paint = new Paint();
                    if (Gdop[i][j] < 0.5)
                    {
                        paint.setColor(Color.GREEN);
                    }
                    else if (Gdop[i][j] > 0.5 || Gdop[i][j] < 0.7)
                    {
                        paint.setColor(Color.YELLOW);
                    }
                    else
                    {
                        paint.setColor(Color.RED);
                    }



                }
        }





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




    public void clear(View view ) {

        for (int i = 0; i < pointImageView.PointList.size(); i++) {


            pointImageView.PointList.get(i).x = 0;
            pointImageView.PointList.get(i).y = 0;
        }
        pointImageView.invalidateImage();
    }

    //Обрабатываем результат выбора в галерее:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case Pick_image:
                if(resultCode == RESULT_OK){
                    try {

                        //Получаем URI изображения, преобразуем его в Bitmap
                        //объект и отображаем в элементе ImageView нашего интерфейса:
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imageView.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }}









}