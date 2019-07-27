package com.example.testingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ChooseAutoBuyMethodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_auto_buy_method);
    }

    public void manualAutoBuyButtonSetup(View view) {
        Intent startIntent = new Intent(getApplicationContext(), ManualAutoBuyActivity.class);
        startActivity(startIntent);
    }
    public void manualAutoBuyButtonSetup(View view) {
        Intent startIntent = new Intent(getApplicationContext(), ManualAutoBuyActivity.class);
        startActivity(startIntent);
    }
}
