package com.example.testingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AutoBuyActivity extends AppCompatActivity {

    ListView myListView;
    public static ArrayList<Pair<String, Double>> mWantedItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_buy);


        TextView headlineTextView = (TextView) findViewById(R.id.activity_auto_buy_headlineTextView);
        TextView nameTextView = (TextView) findViewById(R.id.activity_auto_buy_nameTextView);
        TextView qtyTextView = (TextView) findViewById(R.id.activity_auto_buy_qtyTextView);
        Button addBtn = (Button) findViewById(R.id.activity_auto_buy_addBtn);
        Button startBtn = (Button) findViewById(R.id.activity_auto_buy_startBtn);

        headlineTextView.setText(R.string.automaticShopping);
        nameTextView.setText(R.string.productName);
        qtyTextView.setText(R.string.quantity);

        addBtn.setText(R.string.addProduct);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair<String, Double> def = new Pair<String, Double>("", new Double(0));
                AutoBuyActivity.mWantedItems.add(def); //default

                AutoBuyWantedAdapter autoBuyWantedAdapter = (AutoBuyWantedAdapter) myListView.getAdapter();
                autoBuyWantedAdapter.notifyDataSetChanged();
            }
        });

        startBtn.setText(R.string.startAutoBuy);
        //startBtn.setOnClickListener(...); - does nothing for now

        myListView = findViewById(R.id.activity_auto_buy_wanteditemsListView);

        if(AutoBuyActivity.mWantedItems == null) {
            AutoBuyActivity.mWantedItems = new ArrayList<Pair<String, Double>>();
            Pair<String, Double> def = new Pair<String, Double>("", new Double(0));
            AutoBuyActivity.mWantedItems.add(def); //default
        }

        AutoBuyWantedAdapter autoBuyWantedAdapter = new AutoBuyWantedAdapter(this, AutoBuyActivity.mWantedItems);
        myListView.setAdapter(autoBuyWantedAdapter);

    }
}
