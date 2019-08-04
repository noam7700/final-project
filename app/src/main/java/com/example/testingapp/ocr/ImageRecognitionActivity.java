package com.example.testingapp.ocr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testingapp.AutoBuyActivity;
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
            Button gotoManualBtn = (Button) findViewById(R.id.activity_image_recognition_gotoManualBtn);

            imageView.setImageBitmap(image);

            String imageText = ocrManager.getText(image, this);
            TextView textView = (TextView) findViewById(R.id.imageText);
            textView.setText(imageText);

            gotoManualBtn.setText(R.string.startAutoBuy);
            gotoManualBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ArrayList<Pair<String,Double>> fake = OcrManager.parseTextToPairs(textView.getText().toString());
                    Intent startIntent = new Intent(getApplicationContext(), AutoBuyActivity.class);
                    startIntent.putExtra("com.example.testingapp.FAKE", "lololol");
                    startActivity(startIntent);
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
