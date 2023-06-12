package com.example.menu.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menu.R;
import com.example.menu.models.DrinkDto;
import com.example.menu.models.FoodDto;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private List<FoodDto> foodList;

    public FoodAdapter(List<FoodDto> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout for the ViewHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        FoodDto food = foodList.get(position);
        holder.bind(food);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView priceTextView;
        TextView descriptionTextView;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.food_photo_image_view);
            nameTextView = itemView.findViewById(R.id.food_name_text_view);
            priceTextView = itemView.findViewById(R.id.food_price_text_view);
            descriptionTextView = itemView.findViewById(R.id.food_description_text_view);
        }
        void bind(FoodDto food) {
            nameTextView.setText(food.getName());
            priceTextView.setText(String.valueOf(food.getPrice()));
            descriptionTextView.setText(food.getDescription());
            Picasso.get().load(food.getPhoto()).into(imageView);
        }
    }
}
