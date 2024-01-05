package com.example.finalprojectandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class CommerceCafeteriaActivity extends AppCompatActivity implements RecyclerViewInterface {
    private RecyclerView recyclerView, recyclerView1, recyclerView2, recyclerView3, recyclerView4;
    private List<FoodItem> Sandwiches = new ArrayList<FoodItem>();
    private List<FoodItem> HotDrinks = new ArrayList<FoodItem>();
    private List<FoodItem> ColdDrinks = new ArrayList<FoodItem>();
    private List<FoodItem> Salads = new ArrayList<FoodItem>();
    private List<FoodItem> Sweets = new ArrayList<FoodItem>();
    private RelativeLayout hotSandLayout, sweetsLayout, hotDrinksLayout, coldDrinksLayout, Saladslayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotsandwichesdesign);
        setupviews();
        getSandwiches();
        getHotDrinks();
        getColdDrinks();
        getSalads();
        getSweets();
        showVisibilityLayouts();//this method must be at last line in oncreate();;
    }
    public void getSandwiches() {
        Sandwiches.add(new FoodItem("food1", 1.0, R.drawable.sandwiches));
        Sandwiches.add(new FoodItem("food2", 1.0, R.drawable.sandwiches));
        Sandwiches.add(new FoodItem("food3", 1.0, R.drawable.sandwiches));
        Sandwiches.add(new FoodItem("food4", 1.0, R.drawable.sandwiches));
        recyclerView = findViewById(R.id.HotSandwichesrec);
        final AdapterFoodRecycler adapter = new AdapterFoodRecycler(this, Sandwiches, this);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }
    public void getHotDrinks() {
        HotDrinks.add(new FoodItem("food3", 1.0, R.drawable.hotdrink));
        HotDrinks.add(new FoodItem("food4", 1.0, R.drawable.hotdrink));
        final AdapterFoodRecycler adapter1 = new AdapterFoodRecycler(this, HotDrinks, this);
        recyclerView1 = findViewById(R.id.HotDrinksrec);
        recyclerView1.setAdapter(adapter1);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView1.setLayoutManager(gridLayoutManager2);
        recyclerView1.setHasFixedSize(true);
    }
    public void getColdDrinks() {
        ColdDrinks.add(new FoodItem("food3", 1.0, R.drawable.colddrink));
        ColdDrinks.add(new FoodItem("food4", 1.0, R.drawable.colddrink));
        ColdDrinks.add(new FoodItem("food5", 1.0, R.drawable.colddrink));
        final AdapterFoodRecycler adapter2 = new AdapterFoodRecycler(this, ColdDrinks, this);
        recyclerView2 = findViewById(R.id.coldDrinksrec);
        recyclerView2.setAdapter(adapter2);
        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView2.setLayoutManager(gridLayoutManager3);
        recyclerView2.setHasFixedSize(true);
    }
    public void getSalads() {
        Salads.add(new FoodItem("food3", 1.0, R.drawable.salad));
        Salads.add(new FoodItem("food4", 1.0, R.drawable.salad));
        Salads.add(new FoodItem("food5", 1.0, R.drawable.salad));
        Salads.add(new FoodItem("food5", 1.0, R.drawable.salad));
        final AdapterFoodRecycler adapter3 = new AdapterFoodRecycler(this, Salads, this);
        recyclerView3 = findViewById(R.id.Saladsrec);
        recyclerView3.setAdapter(adapter3);
        GridLayoutManager gridLayoutManager4 = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView3.setLayoutManager(gridLayoutManager4);
        recyclerView3.setHasFixedSize(true);
    }
    public void getSweets() {
        Sweets.add(new FoodItem("food3", 1.0, R.drawable.sweet));
        Sweets.add(new FoodItem("food4", 1.0, R.drawable.sweet));
        Sweets.add(new FoodItem("food5", 1.0, R.drawable.sweet));
        final AdapterFoodRecycler adapter4 = new AdapterFoodRecycler(this, Sweets, this);
        recyclerView4 = findViewById(R.id.sweetsrec);
        recyclerView4.setAdapter(adapter4);
        GridLayoutManager gridLayoutManager5 = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView4.setLayoutManager(gridLayoutManager5);
        recyclerView4.setHasFixedSize(true);
    }
    public void setupviews() {
        hotSandLayout = findViewById(R.id.rl1);
        hotDrinksLayout = findViewById(R.id.rl2);
        coldDrinksLayout = findViewById(R.id.rl3);
        Saladslayout = findViewById(R.id.rl4);
        sweetsLayout = findViewById(R.id.rl5);
    }
    public void showVisibilityLayouts() {
        if (Sandwiches.size() > 0) {
            hotSandLayout.setVisibility(View.VISIBLE);
        }
        if (HotDrinks.size() > 0) {
            hotDrinksLayout.setVisibility(View.VISIBLE);
        }
        if (ColdDrinks.size() > 0) {
            coldDrinksLayout.setVisibility(View.VISIBLE);
        }
        if (Salads.size() > 0) {
            Saladslayout.setVisibility(View.VISIBLE);
        }
        if (Sweets.size() > 0) {
            sweetsLayout.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onCafeteriaClick(int position) {
        Intent intent = new Intent(CommerceCafeteriaActivity.this, foodInformationActivity.class);
        startActivity(intent);
    }
}