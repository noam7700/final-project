package com.example.testingapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class CategoryActivity extends AppCompatActivity {

    ListView myListView;
    Category myCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        forceRTLIfSupported();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myCategory = getIntent().getParcelableExtra("com.example.testingapp.CATEGORY");
        myListView = (ListView) findViewById(R.id.productsListView);

        ProductAdapter productAdapter = new ProductAdapter(this, myCategory);
        myListView.setAdapter(productAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent startIntent = new Intent(getApplicationContext(), ProductActivity.class);
                Product currPd = myCategory.getProducts().get(position);
                startIntent.putExtra("com.example.testingapp.CATEGORY", myCategory);
                startIntent.putExtra("com.example.testingapp.PRODUCT", currPd);
                startActivity(startIntent);
            }
        });

        TextView categoryNameTextView = (TextView) findViewById(R.id.categoryNameTextView);
        categoryNameTextView.setText(myCategory.getCatName());
        categoryNameTextView.setTextColor(Color.GREEN);
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
