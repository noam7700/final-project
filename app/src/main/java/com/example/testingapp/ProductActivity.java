package com.example.testingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProductActivity extends AppCompatActivity {

    Product myPd;
    Category myCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        myPd = getIntent().getParcelableExtra("com.example.testingapp.PRODUCT");
        myCategory = getIntent().getParcelableExtra("com.example.testingapp.CATEGORY");

        //update the textView(s) to the current product
        TextView pdDescTextView = (TextView) findViewById(R.id.pdDescTextView);
        TextView pdSuppDescTextView = (TextView) findViewById(R.id.pdSuppDescTextView);
        TextView pdPriceTextView = (TextView) findViewById(R.id.pdPriceTextView);
        TextView pdPrice_perunitTextView = (TextView) findViewById(R.id.pdPrice_perunitTextView);

        pdDescTextView.setText(myPd.getDesc());
        pdSuppDescTextView.setText(myPd.getSupplier_desc());
        pdPriceTextView.setText(myPd.getPrice_str());
        pdPrice_perunitTextView.setText(myPd.getPrice_perunit_str());

        Button backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), CategoryActivity.class);
                startIntent.putExtra("com.example.testingapp.CATEGORY", myCategory);
                startActivity(startIntent);
            }
        });
    }
}
