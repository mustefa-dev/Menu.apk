package com.example.menu.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menu.models.DrinkDto;
import com.example.menu.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder> {
    private List<DrinkDto> drinks;

    public DrinkAdapter(List<DrinkDto> drinks) {
        this.drinks = drinks;
    }

    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drink, parent, false);
        return new DrinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder holder, int position) {
        DrinkDto drink = drinks.get(position);
        holder.bind(drink);
    }

    @Override
    public int getItemCount() {
        return drinks.size();
    }

    static class DrinkViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView priceTextView;
        private TextView descriptionTextView;
        private ImageView photoImageView;

        DrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.drink_name_text_view);
            priceTextView = itemView.findViewById(R.id.drink_price_text_view);
            descriptionTextView = itemView.findViewById(R.id.drink_description_text_view);
            photoImageView = itemView.findViewById(R.id.drink_photo_image_view);
        }

        void bind(DrinkDto drink) {
            nameTextView.setText(drink.getName());
            priceTextView.setText(String.valueOf(drink.getPrice()));
            descriptionTextView.setText(drink.getDescription());
            Picasso.get().load(drink.getPhoto()).into(photoImageView);
        }
    }
}
