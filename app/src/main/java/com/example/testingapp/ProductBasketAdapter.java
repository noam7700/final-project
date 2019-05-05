package com.example.testingapp;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class ProductBasketAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Basket mBasket;
    private TextView activity_basket_sumTextView;
    private TextView activity_basket_sumDiscountTextView;

    public ProductBasketAdapter(Context c, Basket basket, TextView activity_basket_sumTextView,
                                TextView activity_basket_sumDiscountTextView){
        mBasket = basket; //low copy - important (when deleted here, it should delete the global)
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //used to update overall sum when we update quantity of some product
        this.activity_basket_sumTextView = activity_basket_sumTextView;
        this.activity_basket_sumDiscountTextView = activity_basket_sumDiscountTextView;
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
        final Buyable buyable = mBasket.getBasketBuyables().get(position);

        TextView pdDescTextView = (TextView) view.findViewById(R.id.basketproduct_pdDescTextView);
        TextView quantityTextView = (TextView) view.findViewById(R.id.basketproduct_quantityBtn);
        final EditText quantityEditText = (EditText) view.findViewById(R.id.basketproduct_quantityEditText);
        final TextView pdSumPriceTextView = (TextView) view.findViewById(R.id.basketproduct_pdSumPriceTextView);
        final TextView pdSumDiscountTextView = (TextView) view.findViewById(R.id.basketproduct_pdSumDiscountTextView);
        ImageView basketproduct_ImageView = (ImageView) view.findViewById(R.id.basketproduct_ImageView);

        if(buyable instanceof BasketProduct) {
            Picasso.with(view.getContext()).load(((BasketProduct)buyable).getMyProduct().getImg_src()).into(basketproduct_ImageView);
        }
        pdDescTextView.setText(buyable.getDesc());

        pdSumPriceTextView.setText(String.valueOf(buyable.getPrice()));
        pdSumDiscountTextView.setText(String.valueOf(buyable.getDiscount()) + "-"); //discounts shows as minus

        quantityTextView.setText("כמות:");
        quantityEditText.setText(String.valueOf(buyable.getQuantity()));
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
            public void afterTextChanged(Editable s) { //update quantity
                String newQty = quantityEditText.getText().toString();

                //supports commas in the number. like "1,399.00"
                NumberFormat format = NumberFormat.getInstance(Locale.US);
                Number number = null;
                double newQty_double;
                try {
                    number = format.parse(newQty); //here it might fail
                    newQty_double = number.doubleValue();
                }
                catch (ParseException e) { //if couldn't parse, just say it's 0
                    newQty_double = 0.0;
                    //e.printStackTrace();
                }


                buyable.setQuantity(newQty_double);

                double newPrice = buyable.getPrice();
                pdSumPriceTextView.setText(String.valueOf(newPrice));
                double newDiscount = buyable.getDiscount();
                pdSumDiscountTextView.setText(String.valueOf(newDiscount) + "-"); //discounts shows as minus

                //TODO: update SumPrice efficently (add the delta instead of re-calculating)
                double newSumPrice = mBasket.getPrice();
                activity_basket_sumTextView.setText(String.valueOf(newSumPrice));
                double newSumDiscount = mBasket.getDiscount();
                activity_basket_sumDiscountTextView.setText(String.valueOf(newSumDiscount) + "-"); //discounts shows as minus

            }
        });


        return view;
    }
}
