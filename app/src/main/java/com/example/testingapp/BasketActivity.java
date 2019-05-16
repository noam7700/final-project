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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;

public class BasketActivity extends AppCompatActivity {

    ListView myBasketListView;
    //current basket which is managed (when we add products, add to this global basket).
    //it's static so even if this activity not "open" you can still add products to it.
    //and it won't vanish, while we're still shopping
    public static Basket currentBasket;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        forceRTLIfSupported();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //backbutton

        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_basket);
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

        TextView activity_basket_titleTextView = (TextView) findViewById(R.id.activity_basket_titleTextView);
        TextView activity_basket_sumTextView = (TextView) findViewById(R.id.activity_basket_sumTextView);
        TextView activity_basket_sumDiscountTextView = (TextView) findViewById(R.id.activity_basket_sumDiscountTextView);
        Button activity_basket_buyBtn = (Button) findViewById(R.id.activity_basket_buyBtn);
        Button activity_basket_saveBasketBtn = (Button) findViewById(R.id.activity_basket_saveBasketBtn);
        myBasketListView = (ListView) findViewById(R.id.activity_basket_basketListView);

        activity_basket_titleTextView.setText("הסל שלי");
        double newSumPrice = BasketActivity.currentBasket.getPrice();
        activity_basket_sumTextView.setText(new DecimalFormat("##.##").format(newSumPrice));
        double newDiscount = BasketActivity.currentBasket.getDiscount();
        activity_basket_sumDiscountTextView.setText(new DecimalFormat("##.##").format(newDiscount) + "-"); //discounts shows as minus

        activity_basket_buyBtn.setText("בצע קנייה");
        activity_basket_buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: show on screen all the bought products? :/
            }
        });

        activity_basket_saveBasketBtn.setText("שמור סל");
        activity_basket_saveBasketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: save made basket to the server. and update "My Baskets"
            }
        });


        ProductBasketAdapter productBasketAdapter = new ProductBasketAdapter(this, BasketActivity.currentBasket,
                activity_basket_sumTextView, activity_basket_sumDiscountTextView);
        myBasketListView.setAdapter(productBasketAdapter);
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
