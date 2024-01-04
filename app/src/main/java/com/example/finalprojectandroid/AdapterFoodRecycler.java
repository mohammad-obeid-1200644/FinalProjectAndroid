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
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    List<FoodItem> foodItems;

    public AdapterFoodRecycler( Context context, List<FoodItem> foodItems,RecyclerViewInterface recyclerViewInterface) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.foodItems = foodItems;
    }

    @NonNull
    @Override
    public AdapterFoodRecycler.FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.fooditemrecycler,parent,false);
        return new AdapterFoodRecycler.FoodViewHolder(view,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFoodRecycler.FoodViewHolder holder, int position) {
        holder.FoodNametxt.setText(foodItems.get(position).getFoodName());
        holder.FoodPricetxt.setText(String.valueOf(foodItems.get(position).getFoodPrice()));
        holder.FoodSizetxt.setText(foodItems.get(position).getFoodSize());
        holder.Foodimg.setImageResource(foodItems.get(position).getFoodImg());
    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }
    public static class FoodViewHolder extends RecyclerView.ViewHolder{
        TextView FoodNametxt,FoodPricetxt,FoodSizetxt;
        ImageView Foodimg;
        public FoodViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            FoodNametxt=itemView.findViewById(R.id.txtFoodName);
            FoodPricetxt=itemView.findViewById(R.id.txtfoodprice);
            FoodSizetxt=itemView.findViewById(R.id.txtFoodSize);
            Foodimg=itemView.findViewById(R.id.FoodImg);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface!=null){
                        int position =getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            recyclerViewInterface.onCafeteriaClick(position);
                        }
                    }
                }
            });
        }
    }

}