package com.example.testingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);






        setTitle(R.string.title_shufersal);

        TextView activity_myaccount_helloTextView = (TextView) findViewById(R.id.activity_myaccount_helloTextView);
        activity_myaccount_helloTextView.setText(MainActivity.loggedUser.getUsername());

        Button activity_myaccount_mybasketsBtn = (Button) findViewById(R.id.activity_myaccount_mybasketsBtn);
        activity_myaccount_mybasketsBtn.setText(R.string.mybaskets);
        activity_myaccount_mybasketsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: goto user's baskets
            }
        });

        Button activity_myaccount_historyBtn = (Button) findViewById(R.id.activity_myaccount_historyBtn);
        activity_myaccount_historyBtn.setText(R.string.myhistory);
        activity_myaccount_historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: goto user's history
            }
        });



    }
}
