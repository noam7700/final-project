package com.example.testingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAccountBasketAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<Basket> mBaskets;


    public MyAccountBasketAdapter(Context c, ArrayList<Basket> baskets){
        mBaskets = baskets;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mBaskets.size();
    }

    @Override
    public Object getItem(int position) {
        return mBaskets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.basketitem_layout, null);
        Basket currBasket = mBaskets.get(position);

        TextView basketitem_basketName = (TextView) view.findViewById(R.id.basketitem_basketName);
        TextView basketitem_authorName = (TextView) view.findViewById(R.id.basketitem_authorName);

        basketitem_basketName.setText(currBasket.getName());
        basketitem_authorName.setText(currBasket.getAuthor());

        return view;
    }
}
