package com.example.finalprojectandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

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

public class CafeteriasActivity extends AppCompatActivity implements RecyclerViewInterface2 {
    private static int flag = 0;
    private RecyclerView recyclerView, recyclerView1, recyclerView2, recyclerView3, recyclerView4;
    private List<FoodItem> Sandwiches = new ArrayList<FoodItem>();
    private List<FoodItem> HotDrinks = new ArrayList<FoodItem>();
    private List<FoodItem> ColdDrinks = new ArrayList<FoodItem>();
    private List<FoodItem> Salads = new ArrayList<FoodItem>();
    private List<FoodItem> Sweets = new ArrayList<FoodItem>();
    private static RequestQueue queue;
    private RelativeLayout hotSandLayout, sweetsLayout, hotDrinksLayout, coldDrinksLayout, Saladslayout;
    private AdapterFoodRecycler adapter, adapter1, adapter2, adapter3, adapter4;
    private int[][] posit = new int[5][];
    int counter1 = 0, counter2 = 0, counter3 = 0, counter4 = 0, counter5 = 0;
    private static String result = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menudesign);
        queue = Volley.newRequestQueue(this);
        getfood();
//        Toast.makeText(this,"position: "+pos,Toast.LENGTH_LONG).show();
        setupviews();

        adapter = new AdapterFoodRecycler(this, Sandwiches, this, "0");
        adapter1 = new AdapterFoodRecycler(this, HotDrinks, this, "1");
        adapter2 = new AdapterFoodRecycler(this, ColdDrinks, this, "2");
        adapter3 = new AdapterFoodRecycler(this, Salads, this, "3");
        adapter4 = new AdapterFoodRecycler(this, Sweets, this, "4");


    }


    public void getfood() {
        Sandwiches = new ArrayList<>();
        ColdDrinks = new ArrayList<>();
        HotDrinks = new ArrayList<>();
        Salads = new ArrayList<>();
        Sweets = new ArrayList<>();
        Intent intent = getIntent();
        counter1 = 0;
        counter2 = 0;
        counter3 = 0;
        counter4 = 0;
        counter5 = 0;
        int pos = Integer.valueOf(intent.getStringExtra("pos"));
        String url = "http://10.0.2.2:5000/food/" + pos;
        Log.d("URL_Request", url);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    int j = 0;
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject obj = response.getJSONObject(i);
                        int id = obj.getInt("CafID");
                        if (id == pos) {
                            String Name = obj.getString("FoodName");
                            Log.d("Food_Object", obj.toString());
                            double price = Double.parseDouble(obj.getString("FoodPrice"));
                            int imgNum = Integer.parseInt((obj.getString("FoodImg")));
                            String xxx="";
                            String cat = obj.getString("FoodCategory");

                            if (cat.toLowerCase().trim().equals("sandwiches")) {
                                counter1++;
                                Sandwiches.add(new FoodItem(Name, price,imgNum));
//                                Log.d("imagesTest",R.drawable.p2+"");
                            }
                            if (cat.toLowerCase().equals("cold drinks")) {
                                counter2++;
                                ColdDrinks.add(new FoodItem(Name, price, imgNum));
                            }
                            if (cat.toLowerCase().equals("hot drinks")) {
                                counter3++;
                                HotDrinks.add(new FoodItem(Name, price,imgNum));
                            }
                            if (cat.toLowerCase().equals("salads")) {
                                counter4++;
                                Salads.add(new FoodItem(Name, price, imgNum));
                            }
                            if (cat.toLowerCase().equals("sweets")) {
                                counter5++;
                                Sweets.add(new FoodItem(Name, price, imgNum));
                            }
                        }
                    }


                    posit[0] = new int[counter1];
                    posit[1] = new int[counter3];
                    posit[2] = new int[counter2];
                    posit[3] = new int[counter4];
                    posit[4] = new int[counter5];

                    counter1 = 0;
                    counter2 = 0;
                    counter3 = 0;
                    counter4 = 0;
                    counter5 = 0;


                    for (int i = 0; i < response.length(); i++) {
                        Log.d("mkmkmmkmkmkkm", String.valueOf(i));
                        JSONObject obj = response.getJSONObject(i);
                        int id = obj.getInt("CafID");
                        if (id == pos) {
                            String Name = obj.getString("FoodName");
                            Log.d("Food_Object", obj.toString());
                            double price = Double.parseDouble(obj.getString("FoodPrice"));
                            String cat = obj.getString("FoodCategory");
                            int foodid = obj.getInt("FoodID");
                            if (cat.toLowerCase().equals("sandwiches")) {
                                posit[0][counter1] = foodid;
                                counter1++;
                            }
                            if (cat.toLowerCase().equals("hot drinks")) {
                                posit[1][counter2] = foodid;
                                counter2++;
                            }
                            if (cat.toLowerCase().equals("cold drinks")) {
                                posit[2][counter3] = foodid;
                                counter3++;
                            }

                            if (cat.toLowerCase().equals("salads")) {
                                posit[3][counter4] = foodid;
                                counter4++;
                            }
                            if (cat.toLowerCase().equals("sweets")) {
                                posit[4][counter5] = foodid;
                                counter5++;
                            }
                        }
                    }

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


                    recyclerView2 = findViewById(R.id.coldDrinksrec);
                    recyclerView2.setHasFixedSize(true);
                    GridLayoutManager gridLayoutManager3 = new GridLayoutManager(getApplicationContext(), 2);
                    recyclerView2.setLayoutManager(gridLayoutManager3);
                    recyclerView2.setAdapter(adapter2);


                    recyclerView3 = findViewById(R.id.Saladsrec);
                    recyclerView3.setHasFixedSize(true);
                    GridLayoutManager gridLayoutManager4 = new GridLayoutManager(getApplicationContext(), 2);
                    recyclerView3.setLayoutManager(gridLayoutManager4);
                    recyclerView3.setAdapter(adapter3);


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


    public void onCafeteriaClick(int position, String recyclerViewName) {
//        Toast.makeText(this, "Clicked on item at position " + position + " in recycler view " + recyclerViewName, Toast.LENGTH_LONG).show();
//        Intent inten= getIntent();
//        String xl=inten.getStringExtra("CafeteriaName");
//        Toast.makeText(this,xl, Toast.LENGTH_LONG).show();

        int rec = Integer.parseInt(recyclerViewName);

        String x = "";
        x += posit[rec][position];
        Intent intent = new Intent(CafeteriasActivity.this, foodInformationActivity.class);
        intent.putExtra("mohammad", x);
        Intent hhint=getIntent();
        String pos=hhint.getStringExtra("pos");
        intent.putExtra("Cafpos", pos);
        String la = hhint.getStringExtra("LoggedinUserID");
        intent.putExtra("LoggedinUserID",la);
        startActivity(intent);
    }


    public void backOnClk1(View view) {
        Intent intent=new Intent(CafeteriasActivity.this, CafList.class);
        startActivity(intent);
    }
    public void cartOnClk1(View view) {
        Intent intent=new Intent(CafeteriasActivity.this, CartActivity.class);
        Intent ine = getIntent();
        String la = ine.getStringExtra("LoggedinUserID");
        intent.putExtra("LoggedinUserID",la);
        startActivity(intent);
    }
    public void userOnClk1(View view) {
        Intent intent=new Intent(CafeteriasActivity.this, UserActivity.class);
        Intent ine = getIntent();
        String la = ine.getStringExtra("LoggedinUserID");
        intent.putExtra("LoggedinUserID",la);
        startActivity(intent);
    }

    public void homeOnClk(View view) {
        Intent intent=new Intent(CafeteriasActivity.this, CafList.class);
        startActivity(intent);
    }



}