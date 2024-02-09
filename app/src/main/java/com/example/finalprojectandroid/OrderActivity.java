package com.example.finalprojectandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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

public class OrderActivity extends AppCompatActivity implements RecyclerViewInterface {
    private List<OrderItem> items = new ArrayList<OrderItem>();
    private RecyclerView recyclerView;
    private Adapter adapter;
    private RequestQueue queue;
    int [] ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        queue = Volley.newRequestQueue(this);
        getorders();
        adapter = new Adapter(OrderActivity.this, this, items);
    }

    @Override
    public void onCafeteriaClick(int position) {
//        Log.d("ididiididlala",ids[position]+" , lala");
        Intent intent=new Intent(OrderActivity.this, OrderINFOActivity.class);
        intent.putExtra("orderID",ids[position]+"");
        startActivity(intent);
    }
    public void getorders(){
        Intent hhint=getIntent();
        int la = Integer.valueOf(hhint.getStringExtra("LoggedinUserID"))-1;
        String url = "http://10.0.2.2:5000/orderlist/"+la;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    ids=new int[response.length()];
                    for(int i=0; i < response.length(); i++){
                        JSONObject obj = response.getJSONObject(i);
                        String id = obj.getString("OrderID");
                        String Totpr = obj.getString("TotalPrice");
                        ids[i]=Integer.valueOf(id);
                        items.add(new OrderItem(Integer.valueOf(id),Double.valueOf(Totpr)));
                    }
                    recyclerView = findViewById(R.id.REC);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(OrderActivity.this));


                } catch (JSONException e) {
                    Log.d("Volley_error", e.toString());
                }
            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley_error", error.toString());
                System.out.println("eeee:::::::::::::::::eeeeee");
            }
        });

        queue.add(request);
    }
}
