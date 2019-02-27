package com.example.testingapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ProductActivity extends AppCompatActivity {

    Product myPd;
    Category myCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        forceRTLIfSupported();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //backbutton

        myPd = getIntent().getParcelableExtra("com.example.testingapp.PRODUCT");
        myCategory = getIntent().getParcelableExtra("com.example.testingapp.CATEGORY");

        //update the textView(s) to the current product
        TextView pdDescTextView = (TextView) findViewById(R.id.activity_product_pdDescTextView);
        TextView pdSuppDescTextView = (TextView) findViewById(R.id.activity_product_pdSuppDescTextView);
        TextView pdPriceTextView = (TextView) findViewById(R.id.activity_product_pdPriceTextView);
        TextView pdPrice_perunitTextView = (TextView) findViewById(R.id.activity_product_pdPrice_perunitTextView);
        ImageView pdImgView = (ImageView) findViewById(R.id.activity_pdImageView);
        //setting image view
        Picasso.with(this).load(myPd.getImg_src()).into(pdImgView);


        pdDescTextView.setText(myPd.getDesc());
        pdSuppDescTextView.setText(myPd.getSupplier_desc());
        pdPriceTextView.setText(myPd.getPrice_str());
        pdPrice_perunitTextView.setText(myPd.getPrice_perunit_str());

        Button addBtn = (Button) findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), BasketActivity.class);
                //TODO: add this pd to the basket (using putExtra)
                startActivity(startIntent);
            }
        });

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
