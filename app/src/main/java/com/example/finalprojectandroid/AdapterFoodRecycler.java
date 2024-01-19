package com.example.finalprojectandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterFoodRecycler extends RecyclerView.Adapter<AdapterFoodRecycler.FoodViewHolder> {
    private final RecyclerViewInterface2 recyclerViewInterface;
    private final Context context;
    private final List<FoodItem> foodItems;
    private final String recyclerViewName;

    public AdapterFoodRecycler(Context context, List<FoodItem> foodItems, RecyclerViewInterface2 recyclerViewInterface, String recyclerViewName) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.foodItems = foodItems;
        this.recyclerViewName = recyclerViewName;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fooditemrecycler, parent, false);
        return new FoodViewHolder(view, recyclerViewInterface, recyclerViewName);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        holder.FoodNametxt.setText(foodItems.get(position).getFoodName());
        holder.FoodPricetxt.setText(String.valueOf(foodItems.get(position).getFoodPrice()));
        holder.Foodimg.setImageResource(foodItems.get(position).getFoodImg());
    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView FoodNametxt, FoodPricetxt;
        ImageView Foodimg;
        String recyclerViewName;

        public FoodViewHolder(@NonNull View itemView, RecyclerViewInterface2 recyclerViewInterface, String recyclerViewName) {
            super(itemView);
            this.recyclerViewName = recyclerViewName;
            FoodNametxt = itemView.findViewById(R.id.txtFoodName);
            FoodPricetxt = itemView.findViewById(R.id.txtFoodPrice);
            Foodimg = itemView.findViewById(R.id.FoodImg);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onCafeteriaClick(position, recyclerViewName);
                        }
                    }
                }
            });
        }
    }

}
