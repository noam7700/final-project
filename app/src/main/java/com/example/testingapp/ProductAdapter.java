package com.example.testingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProductAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    Category myCategory;

    public ProductAdapter(Context c, Category myCategory) {
        this.myCategory = myCategory;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return myCategory.getProducts().size();
    }

    @Override
    public Object getItem(int position) {
        return myCategory.getProducts().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.productitem_layout, null);
        Product currProduct = myCategory.getProducts().get(position);
        TextView pdDescTextView = (TextView) view.findViewById(R.id.pdDescTextView);
        TextView pdSuppDescTextView = (TextView) view.findViewById(R.id.pdSuppDescTextView);
        TextView pdPriceTextView = (TextView) view.findViewById(R.id.pdPriceTextView);
        TextView pdPrice_perunitTextView = (TextView) view.findViewById(R.id.pdPrice_perunitTextView);

        //set the textViews
        pdDescTextView.setText(currProduct.getDesc());
        pdSuppDescTextView.setText(currProduct.getSupplier_desc());
        pdPriceTextView.setText(currProduct.getPrice_str());
        pdPrice_perunitTextView.setText(currProduct.getPrice_perunit_str());

        return view;
    }
}
