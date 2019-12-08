package com.example.miniprojetandroid;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ArrayAdapterPerso extends ArrayAdapter <Favories_Bean> {

    public ArrayAdapterPerso (Context context, List<? extends Favories_Bean> myList) {
        super ( context,
                android.R.layout.simple_list_item_2,
                android.R.id.text1,
                (List<Favories_Bean>) myList);
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView text1 = (TextView) view.findViewById(android.R.id.text1);
        TextView text2 = (TextView) view.findViewById(android.R.id.text2);
        text1.setText(getItem(position).getNom());
        text2.setText(getItem(position).getCommune());
        return view;
    }
}
