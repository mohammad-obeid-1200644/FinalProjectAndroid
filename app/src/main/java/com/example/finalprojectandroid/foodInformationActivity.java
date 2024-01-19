package com.example.finalprojectandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class foodInformationActivity extends AppCompatActivity {
    private static RequestQueue queue;

    Button btnDecrement,btnIncrement;
    TextView foodnametxt,foodpricetxt;
    TextView txtQuantity;
    private String name="";
    private double price=0;
    private double u=price;
    int count = 1;
    private static int pos=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_information);
        queue = Volley.newRequestQueue(this);
        //#todo: add setupviews method
        btnDecrement = findViewById(R.id.btnDecrement);
        btnIncrement = findViewById(R.id.btnIncrement);
        txtQuantity = findViewById(R.id.txtQuantity);
        foodnametxt=findViewById(R.id.foodNameTxt);
        foodpricetxt=findViewById(R.id.price);


        getfood();
    }

    public void incrementOnClick(View view){

        u+=price;
        count++;
        foodpricetxt.setText(String.valueOf(u));
        txtQuantity.setText(""+count);

    }

    public void decrementOnClick(View view){

        if(count<=1){
            txtQuantity.setText("1");
            count=1;}
        else {
            u -= price;
            foodpricetxt.setText(String.valueOf(u));
            count--;
            txtQuantity.setText("" + count);
        }
    }




    public void getfood() {
        Intent intent = getIntent();
        int pos = Integer.valueOf(intent.getStringExtra("mohammad"));
        String url = "http://10.0.2.2:5000/food/f/" + pos;
        Log.d("URL_Request", url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {


                            name = response.getString("FoodName");
                            price = response.getDouble("FoodPrice");
                            u=price;
                            foodnametxt.setText(name);
                            foodpricetxt.setText(String.valueOf(price));
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
        queue.add(request);
    }





    public void backtolistclk(View view) {
        Intent inte = new Intent(foodInformationActivity.this, CafeteriasActivity.class);
        startActivity(inte);
    }

    public void AddToCartClk(View view) {
        Intent inte = new Intent(foodInformationActivity.this, CartActivity.class);
        startActivity(inte);
    }
}