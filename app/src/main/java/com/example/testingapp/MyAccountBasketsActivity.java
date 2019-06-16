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

import java.util.ArrayList;

public class MyAccountBasketsActivity extends AppCompatActivity {

    ListView myListView;
    public static ArrayList<Basket> mBaskets;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account_baskets);
        forceRTLIfSupported();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_my_account_baskets);
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


        TextView activity_my_account_baskets_titleTextView = (TextView) findViewById(R.id.activity_my_account_baskets_titleTextView);
        Button activity_my_account_baskets_saveAllBasketsBtn = (Button) findViewById(R.id.activity_my_account_baskets_saveAllBasketsBtn);

        activity_my_account_baskets_titleTextView.setText(R.string.mybaskets);
        activity_my_account_baskets_saveAllBasketsBtn.setText(R.string.saveAllBaskets);
        activity_my_account_baskets_saveAllBasketsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i<mBaskets.size(); i++) {
                    //TODO: check with amit the server - DONE
                    MainActivity.loggedUser.deleteBasket_byIndex(i); //remove old version
                    MainActivity.loggedUser.addBasket(mBaskets.get(i));
                }
            }
        });

        myListView = (ListView) findViewById(R.id.activity_my_account_baskets_basketsListView);

        if(MyAccountBasketsActivity.mBaskets == null) { //first time loading this activity
            mBaskets = new ArrayList<Basket>(); //should be deleted once we un-comment amit's
            //TODO: check with amit the server - DONE
            mBaskets = MainActivity.loggedUser.getSavedBaskets();

            //add custom basket if it was edited before loggining
            if (BasketActivity.currentBasket.getBasketBuyables().size() > 0) //he added some pds already
                mBaskets.add(BasketActivity.currentBasket);
        }

        MyAccountBasketAdapter myAccountBasketAdapter = new MyAccountBasketAdapter(this, mBaskets);
        myListView.setAdapter(myAccountBasketAdapter);
    }

    @Override
    public void onResume(){
        super.onResume();

        //refresh the listView because maybe popUp updated it
        MyAccountBasketAdapter myAccountBasketAdapter = (MyAccountBasketAdapter) myListView.getAdapter();
        myAccountBasketAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_my_account_baskets, menu);
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
                case R.id.menu_my_account_baskets_addBasket:
                    //add new basket
                    String new_basket = getResources().getString(R.string.new_basket);
                    String anonymous = getResources().getString(R.string.anonymous);
                    MyAccountBasketsActivity.mBaskets.add(new Basket(new_basket, anonymous));

                    //cast to our type of adapter to use notifyDataSetChanged...
                    MyAccountBasketAdapter myAccountBasketAdapter = (MyAccountBasketAdapter) myListView.getAdapter();
                    myAccountBasketAdapter.notifyDataSetChanged();
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
