package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;

public class start3 extends AppCompatActivity {
    @SuppressLint("SourceLockedOrientationActivity")

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start3);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    private ImageView userAva;
    private final int PICK_IMAGE = 2;
    private Uri fileUri;

public void im(View View)
{
    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
    photoPickerIntent.setType("image/*");
    startActivityForResult(photoPickerIntent, PICK_IMAGE);

}

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                ///…

                ///
                case PICK_IMAGE:
                    fileUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(fileUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    userAva.setImageBitmap(selectedImage);
                    break;
            }
        }
    }

}
