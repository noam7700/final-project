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
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        forceRTLIfSupported();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_about);
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


        TextView activity_about_titleTextView = (TextView) findViewById(R.id.activity_about_titleTextView);
        activity_about_titleTextView.setText(R.string.about);

        TextView activity_about_contentTextView = (TextView) findViewById(R.id.activity_about_contentTextView);
        activity_about_contentTextView.setText(R.string.about_content);

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
