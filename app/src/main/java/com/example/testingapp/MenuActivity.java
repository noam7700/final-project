package com.example.testingapp;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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


//Show Menu of categories of products
public class MenuActivity extends AppCompatActivity {

    ListView myListView;
    //static because when reopening the activity, shouldn't reload from the text file
    static Shop myAppShop = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        forceRTLIfSupported();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //backbutton

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
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home: //backbutton
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }
}
