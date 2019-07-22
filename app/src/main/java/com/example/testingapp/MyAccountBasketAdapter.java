package com.example.testingapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class MyAccountBasketAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Basket> mBaskets;


    public MyAccountBasketAdapter(Context c, List<Basket> baskets){
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

        TextView basketitem_basketNameBtn = (TextView) view.findViewById(R.id.basketitem_basketNameBtn);
        TextView basketitem_authorNameBtn = (TextView) view.findViewById(R.id.basketitem_authorNameBtn);
        Button basketitem_setBasketBtn = (Button) view.findViewById(R.id.basketitem_setBasketBtn);
        Button basketitem_SaveBasketBtn = (Button) view.findViewById(R.id.basketitem_SaveBasketBtn);
        ImageButton basketitem_deleteImageButton = (ImageButton) view.findViewById(R.id.basketitem_deleteImageButton);

        basketitem_basketNameBtn.setText(currBasket.getName());
        basketitem_basketNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pop EditBasketNamePopActivity - to edit name & creator
                Intent startIntent = new Intent(v.getContext(), EditBasketNamePopActivity.class);
                startIntent.putExtra("BasketIndex", position);
                v.getContext().startActivity(startIntent);
            }
        });

        basketitem_authorNameBtn.setText(currBasket.getAuthor());
        basketitem_authorNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pop EditBasketNamePopActivity - to edit name & creator
                Intent startIntent = new Intent(v.getContext(), EditBasketNamePopActivity.class);
                startIntent.putExtra("BasketIndex", position);
                v.getContext().startActivity(startIntent);
            }
        });

        basketitem_setBasketBtn.setText(R.string.setBasket);
        basketitem_setBasketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: check if previous basket is not saved (and ask him to save it..)
                BasketActivity.currentBasket = currBasket; //change current editable basket to be this basket
                //goto BasketActivity
                Intent startIntent = new Intent(parent.getContext(), BasketActivity.class);
                parent.getContext().startActivity(startIntent);
            }
        });
        basketitem_deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: check with amit the server - DONE
                MainActivity.loggedUser.deleteBasket_byIndex(position);
                mBaskets.remove(position);
                notifyDataSetChanged();
            }
        });
        basketitem_SaveBasketBtn.setText(R.string.saveBasket);
        basketitem_SaveBasketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: check with amit the server - DONE
                MainActivity.loggedUser.deleteBasket_byIndex(position); //remove old version
                MainActivity.loggedUser.addBasket(mBaskets.get(position));
            }
        });

        return view;
    }
}
