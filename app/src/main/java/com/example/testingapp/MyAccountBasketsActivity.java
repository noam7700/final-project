package com.example.testingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAccountBasketsActivity extends AppCompatActivity {

    ListView myListView;
    public static ArrayList<Basket> mBaskets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account_baskets);


        TextView activity_my_account_baskets_titleTextView = (TextView) findViewById(R.id.activity_my_account_baskets_titleTextView);
        activity_my_account_baskets_titleTextView.setText(R.string.mybaskets);
        myListView = (ListView) findViewById(R.id.activity_my_account_baskets_basketsListView);

        if(MyAccountBasketsActivity.mBaskets == null) //first time loading this activity
            //TODO: load from server //mBaskets = MainActivity.loggedUser.getSavedBaskets();
            mBaskets = new ArrayList<Basket>();

        mBaskets.add(new Basket("Bar BQ", "Mr. fluffy"));
        MyAccountBasketAdapter myAccountBasketAdapter = new MyAccountBasketAdapter(this, mBaskets);
        myListView.setAdapter(myAccountBasketAdapter);
    }
}
