package com.example.testingapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class CategoryAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    Shop myShop;

    CategoryAdapter(Context c, Shop myShop){
        this.myShop = myShop;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return myShop.getCategories().size();
    }

    @Override
    public Object getItem(int position) {
        return myShop.getCategories().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.categoryitem_layout, null);

        final Category currCat = this.myShop.getCategories().get(position);
        //set absract button to support what we want (specific name & jump with specific data)
        Button categoryBtn = (Button) view.findViewById(R.id.categoryBtn);
        categoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent();
                startIntent.setClass(v.getContext(), CategoryActivity.class);
                startIntent.putExtra("com.example.testingapp.CATEGORY", currCat); //specific data
                v.getContext().startActivity(startIntent);
            }
        });
        categoryBtn.setText(currCat.getCatName()); //specific name
        return view;
    }
}
