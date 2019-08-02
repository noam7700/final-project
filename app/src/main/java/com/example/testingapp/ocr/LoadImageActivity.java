package com.example.testingapp.ocr;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.testingapp.R;

public class LoadImageActivity extends AppCompatActivity {

    private static final int LOAD_IMG = 1;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_image);

        mImageView = (ImageView) findViewById(R.id.imageView);
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, LOAD_IMG);


    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (reqCode == LOAD_IMG && resultCode == RESULT_OK
                && null != data) {
            final Uri imageUri = data.getData();

            Intent intent = new Intent(this, ImageRecognitionActivity.class);
            intent.putExtra("imageUri", imageUri);
            startActivity(intent);


        } else {
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

}
