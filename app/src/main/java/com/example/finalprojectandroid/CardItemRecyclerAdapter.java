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

public class CardItemRecyclerAdapter extends RecyclerView.Adapter<CartItemViewHolder>{
    Context context;
    List<CartItem> foodItems;

    public CardItemRecyclerAdapter(Context context, List<CartItem> foodItems) {
        this.context = context;
        this.foodItems = foodItems;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        CartItem currentItem = foodItems.get(position);
        holder.CartResturantName.setText(foodItems.get(position).getCafName());
        holder.CartFoodItemName.setText(foodItems.get(position).getFoodName());
        holder.CartItemPrice.setText(String.valueOf(foodItems.get(position).getPrice()));
        holder.CartItemQuantity.setText(String.valueOf(foodItems.get(position).getQuantity()));
        holder.CartItemImage.setImageResource(foodItems.get(position).getImage());
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                System.out.println("djdjjdjd");
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }
}
