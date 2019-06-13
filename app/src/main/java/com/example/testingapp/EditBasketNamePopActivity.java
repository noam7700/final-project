package com.example.testingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditBasketNamePopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_basket_name_pop);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.4));

        int position = getIntent().getIntExtra("BasketIndex", -1);

        Basket basket = MyAccountBasketsActivity.mBaskets.get(position);
        TextView nameHeaderTextView = (TextView) findViewById(R.id.activity_edit_basket_name_pop_nameHeaderTextView);
        TextView authorHeaderTextView = (TextView) findViewById(R.id.activity_edit_basket_name_pop_authorHeaderTextView);
        EditText nameEditText = (EditText) findViewById(R.id.activity_edit_basket_name_pop_nameEditText);
        EditText authorEditText = (EditText) findViewById(R.id.activity_edit_basket_name_pop_authorEditText);
        Button setNewBtn = (Button) findViewById(R.id.activity_edit_basket_name_pop_setNewBtn);

        nameHeaderTextView.setText(R.string.basketName);
        authorHeaderTextView.setText(R.string.basketAuthorName);
        nameEditText.setText(basket.getName()); //"previous" name
        authorEditText.setText(basket.getAuthor()); //"previous" author

        setNewBtn.setText(R.string.set);
        setNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basket.setName(nameEditText.getText().toString());
                basket.setAuthor(authorEditText.getText().toString());
                finish();
            }
        });
    }
}
