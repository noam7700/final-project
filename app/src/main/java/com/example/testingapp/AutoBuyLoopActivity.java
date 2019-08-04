package com.example.testingapp;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AutoBuyLoopActivity extends AppCompatActivity {

    Pair<String, Double> loop_object;
    ArrayList<Buyable> searcheditems;
    ListView myListView;
    public static ArrayList<Buyable> chosenitems = null;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_buy_loop);
        forceRTLIfSupported();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_auto_buy_loop);
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


        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_auto_buy_loop);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();


        TextView indexTextView = (TextView) findViewById(R.id.activity_auto_buy_loop_indexTextView);
        EditText nameEditText = (EditText) findViewById(R.id.activity_auto_buy_loop_nameEditText);
        EditText qtyEditText = (EditText) findViewById(R.id.activity_auto_buy_loop_qtyEditText);
        TextView listtitleTextView = (TextView) findViewById(R.id.activity_auto_buy_loop_listtitleTextView);
        Button searchAgainBtn = (Button) findViewById(R.id.activity_auto_buy_loop_searchAgainBtn);
        Button continueBtn = (Button) findViewById(R.id._activity_auto_buy_loop_continueBtn);


        int loop_index;
        loop_index = getIntent().getIntExtra("com.example.testingapp.LOOPINDEX", -1);
        indexTextView.setText(String.valueOf(loop_index));

        if(AutoBuyLoopActivity.chosenitems == null) {
            AutoBuyLoopActivity.chosenitems = new ArrayList<>(AutoBuyActivity.mWantedItems.size());
        }

        //loop_index starts as 1, and array starts as 0...
        loop_object = AutoBuyActivity.mWantedItems.get(loop_index - 1);

        ArrayList<Product> searchedResult = User.searchWanteditem(loop_object);
        if(searchedResult == null){
            this.searcheditems = null;
        } else {
            this.searcheditems = new ArrayList<Buyable>();

            double wanted_qty = loop_object.second;
            for (int i = 0; i < searchedResult.size(); i++) {
                this.searcheditems.add(new BasketProduct(searchedResult.get(i), wanted_qty));
            }
        }

        // w/o text listeners. to update loop_object you need to press the "search again" button
        nameEditText.setText(loop_object.first);
        qtyEditText.setText(String.valueOf(loop_object.second));

        listtitleTextView.setText(R.string.chooseBestOption);

        myListView = (ListView) findViewById(R.id.activity_auto_buy_loop_searcheditemsListView);
        AutoBuySearcheditemsAdapter autoBuySearcheditemsAdapter =
                new AutoBuySearcheditemsAdapter(this, this.searcheditems);
        myListView.setAdapter(autoBuySearcheditemsAdapter);

        //wanteditem_index = -1; //default, nothing was chosen TODO: make them stay, if it's still -1
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AutoBuySearcheditemsAdapter myadapter = (AutoBuySearcheditemsAdapter) myListView.getAdapter();
                myadapter.setWanteditem_index(position); //set to clicked item
                myadapter.notifyDataSetChanged(); //update views

            }
        });

        searchAgainBtn.setText(R.string.UpdateAndsearchAgain);
        searchAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1. update loop_object (from editTexts)
                String newstring = nameEditText.getText().toString();
                Double newdouble;
                try {
                    newdouble = Double.parseDouble(qtyEditText.getText().toString());
                } catch(Exception ex) { //couldn't parse, probably empty...
                    newdouble = 0.0;
                }
                Pair<String, Double> newpair = new Pair<>(newstring, newdouble);
                AutoBuyActivity.mWantedItems.set(loop_index - 1, newpair);

                loop_object = AutoBuyActivity.mWantedItems.get(loop_index - 1);

                //update wanteditems's list in AutoBuyActivity (if he goes with 'previous')
                AutoBuyWantedAdapter wantedAdapter = (AutoBuyWantedAdapter) AutoBuyActivity.myListView.getAdapter();
                wantedAdapter.notifyDataSetChanged();

                //2. search again
                ArrayList<Product> searchedResult = User.searchWanteditem(loop_object);
                if(searchedResult == null){ //didnt find anything/error?
                    searcheditems = null;
                } else {
                    searcheditems.clear();

                    double wanted_qty = loop_object.second;
                    for (int i = 0; i < searchedResult.size(); i++) {
                        searcheditems.add(new BasketProduct(searchedResult.get(i), wanted_qty));
                    }
                }

                AutoBuySearcheditemsAdapter myadapter = (AutoBuySearcheditemsAdapter) myListView.getAdapter();
                myadapter.notifyDataSetChanged(); //update search result's listview

            }
        });

        if(loop_index < AutoBuyActivity.mWantedItems.size())
            continueBtn.setText(R.string.continueToNextProduct);
        else
            continueBtn.setText(R.string.finishAndCreateBasket);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoBuySearcheditemsAdapter myadapter = (AutoBuySearcheditemsAdapter) myListView.getAdapter();
                int wanted_index = myadapter.getWanteditem_index();
                //TODO: dont allow if nothing was selected
                if(wanted_index == -1){
                    //alert the user for not choosing
                    AlertDialog.Builder builder = new AlertDialog.Builder(AutoBuyLoopActivity.this);

                    builder.setCancelable(true);
                    builder.setTitle(R.string.alert);
                    builder.setMessage(R.string.chooseOptionToContinue);

                    builder.setPositiveButton(R.string.understood, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();

                    return;
                }

                //TODO: add the chosen product to chosenitems (in both cases..)
                Buyable currChosenitem = AutoBuyLoopActivity.this.searcheditems.get(wanted_index);
                AutoBuyLoopActivity.chosenitems.add(currChosenitem);



                if(loop_index < AutoBuyActivity.mWantedItems.size()) {
                    Intent startIntent = new Intent(getApplicationContext(), AutoBuyLoopActivity.class);
                    startIntent.putExtra("com.example.testingapp.LOOPINDEX", loop_index + 1); //next wanteditem
                    startActivity(startIntent);
                } else {
                    //(1)create the Basket
                    String anonymous_str = getString(R.string.anonymous);
                    String autoBuyBasket_str = getString(R.string.automaticBasket);
                    Basket autoBuyBasket = new Basket(autoBuyBasket_str, anonymous_str, chosenitems);

                    //(2)add it to user's baskets
                    if(MainActivity.isLoggedIn) {
                        //TODO: check with amit the server - DONE
                        MainActivity.loggedUser.addBasket(autoBuyBasket);
                        MyAccountBasketsActivity.mBaskets.add(autoBuyBasket); //update in app immediately
                    }
                    //(3)goto BasketActivity
                    BasketActivity.currentBasket = autoBuyBasket;
                    Intent startIntent = new Intent(getApplicationContext(), BasketActivity.class);
                    startActivity(startIntent);
                }
            }
        });


    }

    @Override
    public void onResume(){
        super.onResume();

        int loop_index;
        loop_index = getIntent().getIntExtra("com.example.testingapp.LOOPINDEX", -1);

        /*someone returned to this activity after adding this wanteditem. so delete him, and let him
          start over...*/
        //for example: if loop_index=1, and size is already 1, then wanteditem was already added
        if(AutoBuyLoopActivity.chosenitems.size() >= loop_index)
            AutoBuyLoopActivity.chosenitems.remove(AutoBuyLoopActivity.chosenitems.size()-1);

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
