package com.example.testingapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


//Show Menu of categories of products
public class MenuActivity extends AppCompatActivity {

    ListView myListView;
    Shop myAppShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        BufferedReader bufferReader = null;
        try{
            InputStream productsTextData_is = getAssets().open("ProductsTextData.txt");
            bufferReader = new BufferedReader(new InputStreamReader(productsTextData_is, "UTF-8"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Shop appShop = new Shop(bufferReader);
        TextView loadingTextView = (TextView) findViewById(R.id.loadingTextView);
        loadingTextView.setText("קטגוריות");
        loadingTextView.setBackgroundColor(Color.BLUE);

        myAppShop = appShop;
        myListView = (ListView) findViewById(R.id.categoriesListView);

        CategoryAdapter categoryAdapter = new CategoryAdapter(this, myAppShop);
        myListView.setAdapter(categoryAdapter);
    }

}
