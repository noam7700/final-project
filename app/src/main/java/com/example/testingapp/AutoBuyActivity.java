package com.example.testingapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AutoBuyActivity extends AppCompatActivity {

    ListView myListView;
    public static ArrayList<Pair<String, Double>> mWantedItems;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_buy);
        forceRTLIfSupported();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_auto_buy);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        //navigation menu
        NavigationView navigation_menu = (NavigationView) findViewById(R.id.navigation_view);
        navigation_menu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent startIntent;
                switch(menuItem.getItemId()){
                    case R.id.nav_main:
                        startIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(startIntent);
                        return true;
                    case R.id.nav_menu:
                        startIntent = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(startIntent);
                        return true;
                    case R.id.nav_myaccount:
                        if(MainActivity.isLoggedIn == false) {
                            startIntent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(startIntent);
                        }
                        else{
                            startIntent = new Intent(getApplicationContext(), MyAccountActivity.class);
                            startActivity(startIntent);
                        }
                        return true;
                    default:
                        return false;
                }

            }
        });

        setTitle(R.string.title_shufersal);

        String image_text;
        try {
            image_text = (String) getIntent().getExtras().get("com.example.testingapp.FAKE");
        } catch(Exception e){
            e.printStackTrace(); //when opening the activity w/o a picture (manual autobuy)
            image_text = "";
        }

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
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), AutoBuyLoopActivity.class);
                startIntent.putExtra("com.example.testingapp.LOOPINDEX", 1); //start of loop
                startActivity(startIntent);
            }
        });
        //startBtn.setOnClickListener(...); - does nothing for now

        myListView = findViewById(R.id.activity_auto_buy_wanteditemsListView);

        if(image_text.equals("")) {
            AutoBuyActivity.mWantedItems = new ArrayList<Pair<String, Double>>();
            Pair<String, Double> def = new Pair<String, Double>("", new Double(0));
            AutoBuyActivity.mWantedItems.add(def); //default
        } else{
            AutoBuyActivity.mWantedItems = OcrManager.parseTextToPairs(image_text);
        }

        AutoBuyWantedAdapter autoBuyWantedAdapter = new AutoBuyWantedAdapter(this, AutoBuyActivity.mWantedItems);
        myListView.setAdapter(autoBuyWantedAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_mainactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){ //if it's
            return true;
        }
        else
            switch (item.getItemId()){
                case R.id.menu_mainactivity_myaccount:
                    if(MainActivity.isLoggedIn == false) {
                        Intent startIntent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(startIntent);
                    }
                    else{
                        Intent startIntent = new Intent(getApplicationContext(), MyAccountActivity.class);
                        startActivity(startIntent);
                    }
                    return true;
                case R.id.menu_mainactivity_about:
                    Intent startIntent = new Intent(getApplicationContext(), AboutActivity.class);
                    startActivity(startIntent);
            }

        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }
}

