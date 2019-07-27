package com.example.testingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ChooseAutoBuyMethodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_auto_buy_method);
    }

    public void manualAutoBuyButtonSetup(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        String productsText = editText.getText().toString();
        Intent intent = new Intent(getApplicationContext(), ManualAutoBuyActivity.class);
        intent.putExtra("productsText", productsText);
        startActivity(intent);
    }

    public void takeSnipButtonSetup(View view) {

    }
}
