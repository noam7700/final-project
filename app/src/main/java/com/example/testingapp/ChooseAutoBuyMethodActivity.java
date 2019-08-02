package com.example.testingapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.testingapp.ocr.CameraActivity;
import com.example.testingapp.ocr.ImageRecognitionActivity;
import com.example.testingapp.ocr.LoadImageActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ChooseAutoBuyMethodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_auto_buy_method);
    }

    public void manualAutoBuyButtonSetup(View view) {
        Intent intent = new Intent(getApplicationContext(), AutoBuyActivity.class);
        startActivity(intent);
    }

    public void takeSnipButtonSetup(View view) {
        Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
        startActivity(intent);
    }

    public void loadImageButtonSetup(View view) {
        Intent intent = new Intent(getApplicationContext(), LoadImageActivity.class);
        startActivity(intent);
    }
}
