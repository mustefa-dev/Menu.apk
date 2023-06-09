package com.example.menu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.menu.models.SectionDto;

import java.util.List;

public class SectionAdapter extends ArrayAdapter<SectionDto> {
    private final LayoutInflater inflater;

    public SectionAdapter(Context context, List<SectionDto> sections) {
        super(context, 0, sections);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        SectionDto section = getItem(position);

        TextView sectionNameTextView = convertView.findViewById(android.R.id.text1);
        sectionNameTextView.setText(section.getName());

        return convertView;
    }
}
