package com.example.testingapp.ocr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.testingapp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ImageRecognitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_recognition);
        Uri imageUri = (Uri) (getIntent().getExtras().get("imageUri"));
        try {
            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
            final Bitmap image = BitmapFactory.decodeStream(imageStream);
            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageBitmap(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
