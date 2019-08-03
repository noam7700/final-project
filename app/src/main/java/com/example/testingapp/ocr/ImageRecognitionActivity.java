package com.example.testingapp.ocr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.testingapp.OcrManager;
import com.example.testingapp.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ImageRecognitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);     
        setContentView(R.layout.activity_image_recognition);
        
        OcrManager ocrManager = new OcrManager(this);

        Uri imageUri = (Uri) (getIntent().getExtras().get("imageUri"));
        try {
            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
            final Bitmap image = BitmapFactory.decodeStream(imageStream);
            ImageView imageView = findViewById(R.id.imageView1);

            imageView.setImageBitmap(image);

//            String imageText = ocrManager.getText(image, this);

//            TextView textView = (TextView) findViewById(R.id.imageText);
//            textView.setText(imageText);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
