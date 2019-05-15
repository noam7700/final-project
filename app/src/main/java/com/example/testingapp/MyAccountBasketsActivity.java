package com.example.testingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MyAccountBasketsActivity extends AppCompatActivity {

    ListView myListView;
    ArrayList<Basket> mBaskets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account_baskets);


        myListView = (ListView) findViewById(R.id.activity_my_account_baskets_basketsListView);

        MyAccountBasketAdapter myAccountBasketAdapter = new MyAccountBasketAdapter(this, mBaskets);
        myListView.setAdapter(myAccountBasketAdapter);
    }
}
