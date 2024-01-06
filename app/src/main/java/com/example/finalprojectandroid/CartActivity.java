package com.example.finalprojectandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
     int count=1;
    private List<CartItem> items = new ArrayList<CartItem>();
    private RelativeLayout itemsLayout;
    private Button btnDecrement,btnIncrement;
    private ImageView CartItemImage;
    private TextView CartResturantName, CartFoodItemName,CartItemPrice,CartItemQuantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setupviews();
        getSandwiches();
    }
    public void setupviews() {
        CartItemImage=findViewById(R.id.itemImg);
        CartResturantName = findViewById(R.id.CafName);
        CartFoodItemName = findViewById(R.id.txtItemName);
        CartItemPrice = findViewById(R.id.txtFoodPrice);
        CartItemQuantity = findViewById(R.id.txtQuantity1);
    }

    public void getSandwiches() {
        items.add(new CartItem("Caf1","food1", 1.0,1, R.drawable.sandwiches));
        items.add(new CartItem("Caf2","food1", 1.0,1, R.drawable.sandwiches));
        items.add(new CartItem("Caf3","food1", 1.0,1, R.drawable.sandwiches));
        items.add(new CartItem("Caf4","food1", 1.0,1, R.drawable.sandwiches));
        recyclerView = findViewById(R.id.CartItemRec);
        final CardItemRecyclerAdapter adapter = new CardItemRecyclerAdapter(this, items);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void incrementOnClickcc(View view){
        count+=1;
        CartItemQuantity.setText(""+count);
    }

    public void decrementOnClickcc(View view){
        if(count<=1)
            count=1;
        else
            count-=1;
        CartItemQuantity.setText(""+count);
    }

}