package com.example.testingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AutoBuyLoopActivity extends AppCompatActivity {

    int wanteditem_index;
    ArrayList<Product> searcheditems;
    ListView myListView;
    public static ArrayList<Product> chosenitems = null;

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

        continueBtn.setText(R.string.continueToNextProduct);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loop_index < AutoBuyActivity.mWantedItems.size()) {
                    Intent startIntent = new Intent(getApplicationContext(), AutoBuyLoopActivity.class);
                    startIntent.putExtra("com.example.testingapp.LOOPINDEX", loop_index + 1); //next wanteditem
                    startActivity(startIntent);
                }
            }
        });


    }
}
