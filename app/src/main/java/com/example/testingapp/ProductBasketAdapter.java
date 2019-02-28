package com.example.testingapp;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ProductBasketAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Basket mBasket;

    public ProductBasketAdapter(Context c, Basket basket){
        mBasket = basket; //low copy - important (when deleted here, it should delete the global)
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mBasket.getBasketBuyables().size();
    }

    @Override
    public Object getItem(int position) {
        return mBasket.getBasketBuyables().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.basketproduct_layout, null);
        Buyable buyable = mBasket.getBasketBuyables().get(position);

        TextView pdDescTextView = (TextView) view.findViewById(R.id.basketproduct_pdDescTextView);
        TextView quantityTextView = (TextView) view.findViewById(R.id.basketproduct_quantityBtn);
        final EditText quantityEditText = (EditText) view.findViewById(R.id.basketproduct_quantityEditText);
        final TextView pdSumPriceTextView = (TextView) view.findViewById(R.id.basketproduct_pdSumPriceTextView);
        ImageView basketproduct_ImageView = (ImageView) view.findViewById(R.id.basketproduct_ImageView);

        if(buyable instanceof BasketProduct) {
            Picasso.with(view.getContext()).load(((BasketProduct)buyable).getMyProduct().getImg_src()).into(basketproduct_ImageView);
        }
        pdDescTextView.setText(buyable.getDesc());

        quantityTextView.setText("כמות:");
        quantityEditText.addTextChangedListener(new TextWatcher() {
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
                String newQty = quantityEditText.getText().toString();
                //TODO: update pdSumPriceTextView
                pdSumPriceTextView.setText("0.00");
            }
        });


        return view;
    }
}
