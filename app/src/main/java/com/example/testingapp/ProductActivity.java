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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class ProductActivity extends AppCompatActivity {

    Product myPd;
    Category myCategory;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        forceRTLIfSupported();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //backbutton

        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_product);
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


        myPd = (Product) getIntent().getParcelableExtra("com.example.testingapp.PRODUCT");
        myCategory = getIntent().getParcelableExtra("com.example.testingapp.CATEGORY");

        //update the textView(s) to the current product
        TextView pdDescTextView = (TextView) findViewById(R.id.activity_product_pdDescTextView);
        TextView pdSuppDescTextView = (TextView) findViewById(R.id.activity_product_pdSuppDescTextView);
        TextView pdPriceTextView = (TextView) findViewById(R.id.activity_product_pdPriceTextView);
        TextView pdPrice_perunitTextView = (TextView) findViewById(R.id.activity_product_pdPrice_perunitTextView);
        ImageView pdImgView = (ImageView) findViewById(R.id.activity_pdImageView);
        //setting image view
        Picasso.with(this).load(myPd.getImg_src()).into(pdImgView);


        TextView quantityBtn = (TextView) findViewById(R.id.activity_product_quantityBtn);
        final EditText quantityEditText = (EditText) findViewById(R.id.activity_product_quantityEditText);

        pdDescTextView.setText(myPd.getDesc());
        pdSuppDescTextView.setText(myPd.getSupplier_desc());
        pdPriceTextView.setText(myPd.getPrice_str());
        pdPrice_perunitTextView.setText(myPd.getPrice_perunit_str());
        quantityBtn.setText("כמות:");

        Button updateQuantityBtn = (Button) findViewById(R.id.updateQuantityBtn);
        updateQuantityBtn.setText("עדכן כמות בסל");
        updateQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), BasketActivity.class);
                //TODO: update quantity for this product in BasketActivity.currentbasket

                String new_quantity_str = quantityEditText.getText().toString();
                //supports commas in the number. like "1,399.00"
                NumberFormat format = NumberFormat.getInstance(Locale.US);
                Number number_new_quantity = null;
                try {
                    number_new_quantity = format.parse(new_quantity_str);

                }
                catch (ParseException e) {
                    e.printStackTrace();
                }
                double new_quantity = number_new_quantity.doubleValue();
                new_quantity = (double)((int)(new_quantity)); //make it a whole number - sorry :/

                //check if the product already exists in the basket (from last to first)
                BasketProduct foundPd = null;
                Buyable buyable;
                for(int i=BasketActivity.currentBasket.getBasketBuyables().size() - 1; i>=0; i--){
                    buyable = BasketActivity.currentBasket.getBasketBuyables().get(i);
                    if(buyable instanceof BasketProduct && buyable.getDesc().equals(myPd.getDesc())) {
                        foundPd = (BasketProduct) buyable;
                        break;
                    }
                }
                if(foundPd != null)
                    //only change his attribute
                    foundPd.setQuantity(new_quantity);
                else {
                    //TODO: add this Product to BasketActivity.currentBasket
                    //create BasketProduct object
                    BasketProduct thisBasketProduct = new BasketProduct(myPd, new_quantity);
                    //add this basketProduct to currentBasket
                    BasketActivity.currentBasket.getBasketBuyables().add(thisBasketProduct);
                }
                
                startActivity(startIntent);
            }
        });

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
