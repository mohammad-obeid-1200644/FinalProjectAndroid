package com.example.finalprojectandroid;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartItemViewHolder extends RecyclerView.ViewHolder {
    ImageView CartItemImage;
    TextView CartResturantName, CartFoodItemName,CartItemPrice,CartItemQuantity;


    public CartItemViewHolder(@NonNull View itemView)  {
        super(itemView);
        CartItemImage=itemView.findViewById(R.id.itemImg);
        CartResturantName = itemView.findViewById(R.id.CafName);
        CartFoodItemName = itemView.findViewById(R.id.txtItemName);
        CartItemPrice = itemView.findViewById(R.id.txtFoodPrice);
        CartItemQuantity = itemView.findViewById(R.id.txtQuantity1);
    }
}
