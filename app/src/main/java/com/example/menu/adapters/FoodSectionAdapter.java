package com.example.menu.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menu.R;
import com.example.menu.models.FoodSectionDto;

import java.util.List;

public class FoodSectionAdapter extends RecyclerView.Adapter<FoodSectionAdapter.SectionViewHolder> {
    private List<FoodSectionDto> sectionList;
    private OnSectionClickListener clickListener;

    public FoodSectionAdapter(List<FoodSectionDto> sectionList, OnSectionClickListener clickListener) {
        this.sectionList = sectionList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public SectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_section, parent, false);
        return new SectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionViewHolder holder, int position) {
        FoodSectionDto section = sectionList.get(position);
        holder.bind(section);
    }

    @Override
    public int getItemCount() {
        return sectionList.size();
    }

    public class SectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView sectionTextView;

        public SectionViewHolder(@NonNull View itemView) {
            super(itemView);
            sectionTextView = itemView.findViewById(R.id.section_name_text_view);
            itemView.setOnClickListener(this);
        }

        public void bind(FoodSectionDto section) {
            sectionTextView.setText(section.getName());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                FoodSectionDto section = sectionList.get(position);
                clickListener.onSectionClick(section);
            }
        }
    }

    public interface OnSectionClickListener {
        void onSectionClick(FoodSectionDto section);
    }
}
