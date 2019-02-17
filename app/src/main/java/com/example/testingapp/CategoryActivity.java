package com.example.testingapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class CategoryActivity extends AppCompatActivity {

    ListView myListView;
    Category myCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        myCategory = getIntent().getParcelableExtra("com.example.testingapp.CATEGORY");
        myListView = (ListView) findViewById(R.id.productsListView);

        ProductAdapter productAdapter = new ProductAdapter(this, myCategory);
        myListView.setAdapter(productAdapter);

        TextView categoryNameTextView = (TextView) findViewById(R.id.categoryNameTextView);
        categoryNameTextView.setText(myCategory.getCatName());
        categoryNameTextView.setTextColor(Color.BLUE);
        Button backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(startIntent);
            }
        });
    }
}
