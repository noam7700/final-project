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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        forceRTLIfSupported();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //backbutton

        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_login);
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

        EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        EditText passloginPassword = (EditText) findViewById(R.id.passwordEditText);

        final Button loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                //TODO: update user's data(maybe an object)
                startActivity(startIntent);
            }
        });
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
