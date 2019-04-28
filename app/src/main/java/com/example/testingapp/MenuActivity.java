package com.example.testingapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


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
                    case R.id.nav_login:
                        startIntent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(startIntent);
                        return true;
                    default:
                        return false;
                }

            }
        });

        if(MenuActivity.myAppShop == null) { //if it wasn't loaded before, load it
            BufferedReader bufferReader = null;
            try {
                InputStream productsTextData_is = getAssets().open("ProductsTextData.txt");
                bufferReader = new BufferedReader(new InputStreamReader(productsTextData_is, "UTF-8"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Shop appShop = new Shop(bufferReader);
            MenuActivity.myAppShop = appShop;
        }

        TextView loadingTextView = (TextView) findViewById(R.id.loadingTextView);
        loadingTextView.setText("קטגוריות");
        loadingTextView.setBackgroundColor(Color.GREEN);

        //setting the listView to depend on the (possibly new) loaded appShop
        myListView = (ListView) findViewById(R.id.categoriesListView);

        CategoryAdapter categoryAdapter = new CategoryAdapter(this, MenuActivity.myAppShop);
        myListView.setAdapter(categoryAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
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
