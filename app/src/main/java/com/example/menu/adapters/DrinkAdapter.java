package com.example.menu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.menu.models.DrinkDto;

import java.util.List;

public class DrinkAdapter extends ArrayAdapter<DrinkDto> {
    private final LayoutInflater inflater;

    public DrinkAdapter(Context context, List<DrinkDto> drinks) {
        super(context, 0, drinks);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        DrinkDto drink = getItem(position);

        TextView drinkNameTextView = convertView.findViewById(android.R.id.text1);
        drinkNameTextView.setText(drink.getName());

        return convertView;
    }
}
