package com.example.finalprojectandroid;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FoodViewHolder extends RecyclerView.ViewHolder {
    TextView FoodName, Foodprice;
    ImageView Foodimg;

    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);
        Foodimg=itemView.findViewById(R.id.FoodImg);
        FoodName = itemView.findViewById(R.id.txtFoodName);
        Foodprice = itemView.findViewById(R.id.txtFoodPrice);
    }
}