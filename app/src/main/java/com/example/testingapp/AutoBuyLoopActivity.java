package com.example.testingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AutoBuyLoopActivity extends AppCompatActivity {

    ArrayList<Buyable> searcheditems;
    ListView myListView;
    public static ArrayList<Buyable> chosenitems = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_buy_loop);

        TextView indexTextView = (TextView) findViewById(R.id.activity_auto_buy_loop_indexTextView);
        EditText nameEditText = (EditText) findViewById(R.id.activity_auto_buy_loop_nameEditText);
        EditText qtyEditText = (EditText) findViewById(R.id.activity_auto_buy_loop_qtyEditText);
        TextView listtitleTextView = (TextView) findViewById(R.id.activity_auto_buy_loop_listtitleTextView);
        Button continueBtn = (Button) findViewById(R.id._activity_auto_buy_loop_continueBtn);


        int loop_index;
        loop_index = getIntent().getIntExtra("com.example.testingapp.LOOPINDEX", -1);
        indexTextView.setText(String.valueOf(loop_index));

        if(AutoBuyLoopActivity.chosenitems == null) {
            AutoBuyLoopActivity.chosenitems = new ArrayList<>(AutoBuyActivity.mWantedItems.size());
        }

        //loop_index starts as 1, and array starts as 0...
        Pair<String, Double> loop_object = AutoBuyActivity.mWantedItems.get(loop_index - 1);

        if(MainActivity.isLoggedIn) {
            ArrayList<Product> searchedResult = MainActivity.loggedUser.searchWanteditem(loop_object);
            if(searchedResult == null){
                this.searcheditems = null;
            } else {
                this.searcheditems = new ArrayList<Buyable>(searchedResult.size());

                double wanted_qty = loop_object.second;
                for (int i = 0; i < searchedResult.size(); i++) {
                    this.searcheditems.set(i, new BasketProduct(searchedResult.get(i), wanted_qty));
                }
            }
        }
        else {
            this.searcheditems = null;

            //for debugging:
            //custom search result :P
            Product pd1 = MenuActivity.myAppShop.getCategories().get(0).getProducts().get(0);
            Product pd2 = MenuActivity.myAppShop.getCategories().get(0).getProducts().get(1);
            Product pd3 = MenuActivity.myAppShop.getCategories().get(0).getProducts().get(2);
            ArrayList<Product> searchedResult = new ArrayList<>();
            searchedResult.add(pd1); searchedResult.add(pd2); searchedResult.add(pd3);

            this.searcheditems = new ArrayList<Buyable>();

            double wanted_qty = loop_object.second;
            for (int i = 0; i < searchedResult.size(); i++) {
                this.searcheditems.add(new BasketProduct(searchedResult.get(i), wanted_qty));
            }

        }
        nameEditText.setText(loop_object.first);
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                return;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                return;
            }

            @Override
            public void afterTextChanged(Editable s) {
                //TODO: reset searcheditems
            }
        });

        qtyEditText.setText(String.valueOf(loop_object.second));
        qtyEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                return;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                return;
            }

            @Override
            public void afterTextChanged(Editable s) {
                //TODO: reset searcheditems
            }
        });

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
}
