package com.example.testingapp.ocr;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.testingapp.R;

import java.io.File;

public class ImageRecognitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_recognition);
        Bitmap image = (Bitmap) (getIntent().getExtras().get("image"));

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageBitmap(image);

    }
}
