package com.example.testingapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import communicationObjects.ProductInfo;


//Show Menu of categories of products
public class MenuActivity extends AppCompatActivity {

    ListView myListView;
    //static because when reopening the activity, shouldn't reload from the text file
    static Shop myAppShop = null;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        forceRTLIfSupported();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //backbutton

        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_menu);
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

        Handler load_shop_handler = new Handler();

        //setting the listView to depend on the (possibly new) loaded appShop
        myListView = (ListView) findViewById(R.id.categoriesListView);

        if(MenuActivity.myAppShop == null) { //if it wasn't loaded before, load it
            //Shop loading should be done not in main thread!

            myListView.setVisibility(View.INVISIBLE); //until the shop is loaded

            new Thread(new Runnable() {
                @Override
                public void run() {
                    //I'm using user because he has the correct default local IP - 10.100.102.4
                    List<ProductInfo> serverResult = new User("default", "default").getProductsData();
                    MenuActivity.myAppShop = new Shop(serverResult);

                    //if didn't succeed (server's not working)
                    //this piece of code actually never is executed, because when server's down, there's
                    //no timeout in request, and the app crashes.
                    if(MenuActivity.myAppShop.getCategories().size() == 0){
                        //load offline
                        BufferedReader bufferReader = null;
                        try {
                            InputStream productsTextData_is = getAssets().open("ProductsTextData.txt");
                            bufferReader = new BufferedReader(new InputStreamReader(productsTextData_is, "UTF-8"));
                        } catch(IOException e) {
                            e.printStackTrace();
                        }
                        MenuActivity.myAppShop = new Shop(bufferReader);
                    }

                    //Shop is loaded. now reset the listview
                    load_shop_handler.post(new Runnable() {
                        @Override
                        public void run() {
                            CategoryAdapter categoryAdapter = new CategoryAdapter(MenuActivity.this, MenuActivity.myAppShop);
                            myListView.setAdapter(categoryAdapter);

                            findViewById(R.id.loading_animation).setVisibility(View.GONE);
                            myListView.setVisibility(View.VISIBLE);
                        }
                    });

                }
            }).start();

        } else { //Shop != null. it is already loaded
            CategoryAdapter categoryAdapter = new CategoryAdapter(this, MenuActivity.myAppShop);
            myListView.setAdapter(categoryAdapter);

            //dont show loading animation - Shop is already loaded...
            findViewById(R.id.loading_animation).setVisibility(View.GONE);
        }

        TextView loadingTextView = (TextView) findViewById(R.id.loadingTextView);
        loadingTextView.setText("קטגוריות");
        loadingTextView.setBackgroundColor(Color.GREEN);
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
