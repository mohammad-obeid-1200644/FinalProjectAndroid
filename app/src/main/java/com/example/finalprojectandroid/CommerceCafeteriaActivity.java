package com.example.finalprojectandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CommerceCafeteriaActivity extends AppCompatActivity implements RecyclerViewInterface {
    private RecyclerView recyclerView, recyclerView1, recyclerView2, recyclerView3, recyclerView4;
    private List<FoodItem> Sandwiches = new ArrayList<FoodItem>();
    private List<FoodItem> HotDrinks = new ArrayList<FoodItem>();
    private List<FoodItem> ColdDrinks = new ArrayList<FoodItem>();
    private List<FoodItem> Salads = new ArrayList<FoodItem>();
    private List<FoodItem> Sweets = new ArrayList<FoodItem>();
    private static RequestQueue queue;
    private RelativeLayout hotSandLayout, sweetsLayout, hotDrinksLayout, coldDrinksLayout, Saladslayout;
    private AdapterFoodRecycler adapter,adapter1,adapter2,adapter3,adapter4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getfood();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotsandwichesdesign);
        queue = Volley.newRequestQueue(this);

//        Toast.makeText(this,"position: "+pos,Toast.LENGTH_LONG).show();
        setupviews();

        adapter = new AdapterFoodRecycler(this, Sandwiches, this);
        adapter1 = new AdapterFoodRecycler(this, HotDrinks, this);
        adapter2 = new AdapterFoodRecycler(this, ColdDrinks, this);
        adapter3 = new AdapterFoodRecycler(this, Salads, this);
        adapter4 = new AdapterFoodRecycler(this, Sweets, this);

    }
//    public void getfood(){
//        Sandwiches=new ArrayList<>();
//        ColdDrinks=new ArrayList<>();
//        HotDrinks=new ArrayList<>();
//        Salads=new ArrayList<>();
//        Sweets=new ArrayList<>();
//        String url = "http://10.0.2.2:5000/food";
//        Log.d("kkkkkkkkkkkkkkk",url);
//        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
//                null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                try {
//                    int i=0;
//                    while (i<response.length()) {
//                        JSONObject obj = response.getJSONObject(i);
//                        String Name = obj.getString("FoodName");
//                        Log.d("lllllllllllllllll", String.valueOf(obj));
//                        double price =Double.valueOf(obj.getString("FoodPrice"));
////                        food.add(new FoodItem(Name,price,2131165421));
//                        String cat=obj.getString("FoodCategory");
//
//                        if(cat.toLowerCase().equals("sandwiches")){
//                            Sandwiches.add(new FoodItem(Name,price,2131165421));
//                        }
//                        if(cat.toLowerCase().equals("cold drinks")){
//                            ColdDrinks.add(new FoodItem(Name,price,2131165421));
//                        }
//                        if(cat.toLowerCase().equals("hot drinks")){
//                            HotDrinks.add(new FoodItem(Name,price,2131165421));
//                        }
//                        if(cat.toLowerCase().equals("salad")){
//                            Salads.add(new FoodItem(Name,price,2131165421));
//                        }
//                        if(cat.toLowerCase().equals("sweets")){
//                            Sweets.add(new FoodItem(Name,price,2131165421));
//                        }
//
//                        i++;
//                    }
//
//                } catch (JSONException e) {
//                    Log.d("Volley_error", e.toString());
//                }
//            }}, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("Volley_error", error.toString());
//                System.out.println("eeee:::::::::::::::::eeeeee");
//            }
//        });
//
//        // Add the request to the request queue
//        queue.add(request);
//    }
public void getfood(){
//    Sandwiches = new ArrayList<>();
//    ColdDrinks = new ArrayList<>();
//    HotDrinks = new ArrayList<>();
//    Salads = new ArrayList<>();
//    Sweets = new ArrayList<>();
    Intent intent = getIntent();
    int pos=Integer.valueOf(intent.getStringExtra("pos"));
    String url = "http://10.0.2.2:5000/food";
    Log.d("URL_Request", url);
    JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
            null, new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            try {
                int j=0;
                for (int i = 0; i < response.length(); i++) {
                    JSONObject obj = response.getJSONObject(i);
                    int id=obj.getInt("CafID");
                    if(id==pos) {
                        String Name = obj.getString("FoodName");
                        Log.d("Food_Object", obj.toString());
                        double price = Double.parseDouble(obj.getString("FoodPrice"));

                        String cat=obj.getString("FoodCategory");

                        if(cat.toLowerCase().equals("sandwiches")){
                            Sandwiches.add(new FoodItem(Name,price,2131165421));
                        }
                        if(cat.toLowerCase().equals("cold drinks")){
                            ColdDrinks.add(new FoodItem(Name,price,2131165421));
                        }
                        if(cat.toLowerCase().equals("hot drinks")){
                            HotDrinks.add(new FoodItem(Name,price,2131165421));
                        }
                        if(cat.toLowerCase().equals("salad")){
                            Salads.add(new FoodItem(Name,price,2131165421));
                        }
                        if(cat.toLowerCase().equals("sweets")){
                            Sweets.add(new FoodItem(Name,price,2131165421));
                        }
                    }
                }
//                recyclerView=findViewById(R.id.cafkistrec);// todo: add setupviews method
//                recyclerView.setAdapter(adapter);
//                recyclerView.setLayoutManager(new LinearLayoutManager(CafList.this));
//
//
                recyclerView = findViewById(R.id.HotSandwichesrec);
                recyclerView.setHasFixedSize(true);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setAdapter(adapter);


                recyclerView1 = findViewById(R.id.HotDrinksrec);
                recyclerView1.setHasFixedSize(true);
                GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getApplicationContext(), 2);
                recyclerView1.setLayoutManager(gridLayoutManager2);
                recyclerView1.setAdapter(adapter1);
//
//
//
                recyclerView2 = findViewById(R.id.coldDrinksrec);
                recyclerView2.setHasFixedSize(true);
                GridLayoutManager gridLayoutManager3 = new GridLayoutManager(getApplicationContext(), 2);
                recyclerView2.setLayoutManager(gridLayoutManager3);
                recyclerView2.setAdapter(adapter2);
//
//
//
//
//
                recyclerView3 = findViewById(R.id.Saladsrec);
                recyclerView3.setHasFixedSize(true);
                GridLayoutManager gridLayoutManager4 = new GridLayoutManager(getApplicationContext(), 2);
                recyclerView3.setLayoutManager(gridLayoutManager4);
                recyclerView3.setAdapter(adapter3);

//
//
//
//
                recyclerView4 = findViewById(R.id.sweetsrec);
                recyclerView4.setHasFixedSize(true);
                GridLayoutManager gridLayoutManager5 = new GridLayoutManager(getApplicationContext(), 2);
                recyclerView4.setLayoutManager(gridLayoutManager5);
                recyclerView4.setAdapter(adapter4);

                showVisibilityLayouts();//this method must be at last line in oncreate();;


            } catch (JSONException e) {
                Log.e("JSON_Parsing_Error", e.toString());
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("Network_Error", error.toString());
        }
    });

    // Ensure queue is initialized
    if (queue == null) {
        queue = Volley.newRequestQueue(getApplicationContext());
    }
    queue.add(request);
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