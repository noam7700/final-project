package com.example.testingapp;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AutoBuySearcheditemsAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private ArrayList<Buyable> mSearcheditems;
    private int wanteditem_index;

    public AutoBuySearcheditemsAdapter(Context c, ArrayList<Buyable> searcheditems){
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mSearcheditems = searcheditems; //low copy
        wanteditem_index = -1; //default
    }

    public void setWanteditem_index(int new_index){
        this.wanteditem_index = new_index;
    }

    public int getWanteditem_index(){
        return this.wanteditem_index;
    }

    @Override
    public int getCount() {
        if(mSearcheditems != null)
            return mSearcheditems.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        return mSearcheditems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //same layout as BasketActivity's list
        View view = mInflater.inflate(R.layout.basketproduct_layout, null);
        final Buyable searcheditem = mSearcheditems.get(position);

        TextView pdDescTextView = (TextView) view.findViewById(R.id.basketproduct_pdDescTextView);
        TextView quantityTextView = (TextView) view.findViewById(R.id.basketproduct_quantityTextView);
        final EditText quantityEditText = (EditText) view.findViewById(R.id.basketproduct_quantityEditText);
        final TextView pdSumPriceTextView = (TextView) view.findViewById(R.id.basketproduct_pdSumPriceTextView);
        final TextView pdSumDiscountTextView = (TextView) view.findViewById(R.id.basketproduct_pdSumDiscountTextView);
        ImageView basketproduct_ImageView = (ImageView) view.findViewById(R.id.basketproduct_ImageView);
        ImageButton basketproduct_deleteProductBtn = (ImageButton) view.findViewById(R.id.basketproduct_deleteProductBtn);
        Handler discount_change_handler = new Handler();

        if(searcheditem instanceof BasketProduct) {
            Picasso.with(view.getContext()).load(((BasketProduct)searcheditem).getMyProduct().getImg_src()).into(basketproduct_ImageView);
        }

        //not used in here! - cant delete searched results
        if(position != this.wanteditem_index)
            basketproduct_deleteProductBtn.setVisibility(View.INVISIBLE);
        else {
            basketproduct_deleteProductBtn.setVisibility(View.VISIBLE);
            basketproduct_deleteProductBtn.setImageResource(R.drawable.ic_beenhere_black_24dp);
        }
        pdDescTextView.setText(searcheditem.getDesc());

        pdSumPriceTextView.setText(new DecimalFormat("##.##").format(searcheditem.getPrice()));

        pdSumDiscountTextView.setText("loading...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                double discount = searcheditem.getDiscount(1); //currBasket's "qty" is 1 (times_ordered)
                discount_change_handler.post(new Runnable() {
                    @Override
                    public void run() {
                        pdSumDiscountTextView.setText(new DecimalFormat("##.##")
                                .format(discount + "-")); //discounts shows as minus

                    }
                });
            }
        }).start();

        //not used to change quantity, only through the activity ("AutoBuyLoopActivity")
        quantityTextView.setVisibility(View.INVISIBLE);
        quantityEditText.setVisibility(View.INVISIBLE);


        return view;
    }
}
