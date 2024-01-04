package com.example.finalprojectandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class CommerceCafeteriaActivity extends AppCompatActivity implements RecyclerViewInterface{
    private RecyclerView recyclerView;
    private List<FoodItem> foodItems=new ArrayList<FoodItem>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commerce_cafeteria);
        foodItems.add(new FoodItem("food1",1.0,"",R.drawable.baseline_restaurant_24));
        foodItems.add(new FoodItem("food2",1.0,"",R.drawable.baseline_restaurant_24));
        foodItems.add(new FoodItem("food3",1.0,"",R.drawable.baseline_restaurant_24));
        foodItems.add(new FoodItem("food4",1.0,"",R.drawable.baseline_restaurant_24));
        foodItems.add(new FoodItem("food5",1.0,"",R.drawable.baseline_restaurant_24));

        recyclerView=findViewById(R.id.FoodRecycler);
        AdapterFoodRecycler adapter=new AdapterFoodRecycler(this,foodItems,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onCafeteriaClick(int position) {
//
    }
}