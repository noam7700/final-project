package com.example.testingapp;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class AutoBuyWantedAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<Pair<String, Integer>> mWantedItems;

    public AutoBuyWantedAdapter(Context c, ArrayList<Pair<String, Integer>> wantedItems){
        mWantedItems = wantedItems;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() { return mWantedItems.size(); }

    @Override
    public Object getItem(int position) { return mWantedItems.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.autobuy_wanteditem_layout, null);

        Pair<String, Integer> wanteditem = mWantedItems.get(position);

        TextView numberTextView = (TextView) view.findViewById(R.id.autobuy_wanteditem_numberTextView);
        EditText nameEditText = (EditText) view.findViewById(R.id.autobuy_wanteditem_nameEditText);
        EditText qtyEditText = (EditText) view.findViewById(R.id.autobuy_wanteditem_qtyEditText);
        ImageButton deleteImageButton = (ImageButton) view.findViewById(R.id.autobuy_wanteditem_deleteImageButton);

        numberTextView.setText(String.valueOf(position+1)); //counter of wantedItems - start from 1
        nameEditText.setText(wanteditem.first);
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
                String newName = nameEditText.getText().toString();
                String newQty_str = qtyEditText.getText().toString();
                Integer newQty;
                if(newQty_str.equals(""))
                    newQty = Integer.valueOf(0);
                else
                    newQty = Integer.parseInt(newQty_str);

                Pair<String, Integer> newPair = new Pair<String, Integer>(newName, newQty);
                mWantedItems.set(position, newPair);
            }
        });

        qtyEditText.setText(String.valueOf(wanteditem.second));
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
                String newName = nameEditText.getText().toString();
                String newQty_str = qtyEditText.getText().toString();
                Integer newQty;
                if(newQty_str.equals(""))
                    newQty = Integer.valueOf(0);
                else
                    newQty = Integer.parseInt(newQty_str);

                Pair<String, Integer> newPair = new Pair<String, Integer>(newName, newQty);
                mWantedItems.set(position, newPair);
            }
        });

        deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWantedItems.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
