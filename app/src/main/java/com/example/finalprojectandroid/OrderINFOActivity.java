package com.example.finalprojectandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

public class OrderINFOActivity extends AppCompatActivity {
    private List<OrderInformation> items = new ArrayList<OrderInformation>();
    private RecyclerView recyclerView;
    private LastAdapter adapter;
    private RequestQueue queue;
    TextView ordernumber, totalText;
    double total1;
    String price="";
    String Quant="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_infoactivity);
        queue = Volley.newRequestQueue(this);
        getOrderInfoByID();
        adapter = new LastAdapter(getApplicationContext(),items);
        ordernumber = findViewById(R.id.ordernumber);
        totalText = findViewById(R.id.totalText);
    }

    public void getOrderInfoByID(){
        Intent hhint=getIntent();
        int la = Integer.valueOf(hhint.getStringExtra("orderID"));

//        Log.d("ididiididlala",la+" , lala");

        String url = "http://10.0.2.2:5000/orderspec/"+la;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                double total1 = 0;
                try {
                    for(int i=0; i < response.length(); i++){
                        JSONObject obj = response.getJSONObject(i);
                        String cafName = obj.getString("CafName");
                        String foodName = obj.getString("Name");
                        Quant = obj.getString("Quantity");
                        price = obj.getString("Total Price");
                        String img = obj.getString("imgID");
                        double p = Double.valueOf(price);
                        total1 += p;
                        items.add(new OrderInformation(foodName,Integer.valueOf(Quant),Double.valueOf(price),cafName,Integer.valueOf(img)));
                    }
                    recyclerView = findViewById(R.id.LastREC);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(OrderINFOActivity.this));
                    ordernumber.setText("Order Number: "+la);
                    totalText.setText("Total price: "+total1);



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

    public void cartClk(View view) {
        Intent intent=new Intent(OrderINFOActivity.this, CartActivity.class);
        Intent ine = getIntent();
        String la = ine.getStringExtra("LoggedinUserID");
        intent.putExtra("LoggedinUserID",la);
        startActivity(intent);
    }
    public void userClk(View view) {
        Intent intent=new Intent(OrderINFOActivity.this, UserActivity.class);
        Intent ine = getIntent();
        String la = ine.getStringExtra("LoggedinUserID");
        intent.putExtra("LoggedinUserID",la);
        Log.d("sllalllalalalpwppw",la+"l");
        startActivity(intent);
    }
    public void homeClk(View view) {
        Intent intent=new Intent(OrderINFOActivity.this, CafList.class);
        Intent ine = getIntent();
        String la = ine.getStringExtra("LoggedinUserID");
        intent.putExtra("LoggedinUserID",la);
        startActivity(intent);
    }

    public void backClk(View view) {
        Intent intent=new Intent(OrderINFOActivity.this, CafList.class);
        Intent ine = getIntent();
        String la = ine.getStringExtra("LoggedinUserID");
        intent.putExtra("LoggedinUserID",la);
        startActivity(intent);
    }
}

