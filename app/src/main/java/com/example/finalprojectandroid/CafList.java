package com.example.finalprojectandroid;


import androidx.appcompat.app.AppCompatActivity;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CafList extends AppCompatActivity implements RecyclerViewInterface{
    private RecyclerView recyclerView;
    private RequestQueue queue;
    private AdapterRec adapter;
    private List<item> Cafs=new ArrayList<item>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caf_list);
        queue = Volley.newRequestQueue(this);

//        Cafs.add(new item("Commerce","Jammal Naser"));
//        Cafs.add(new item("Science","Jammal Naser"));
//        Cafs.add(new item("Literature","Rifaq For Trading Company"));
//        Cafs.add(new item("Meramie Cafe","Food Cort"));
//        Cafs.add(new item("Top Shawerma","Food Cort"));
//        Cafs.add(new item("Ranoosh cocktail ","Food Cort"));
//        Cafs.add(new item("Natella","Food Cort"));
//        Cafs.add(new item("Toast","Food Cort"));
//        Cafs.add(new item("Burger","Food Cort"));
//        Cafs.add(new item("Grand Falafel","Abbas Alaloul"));
//        Cafs.add(new item("Aldom","Chef Ramzy"));
        getcafs();

        adapter=new AdapterRec(CafList.this,Cafs,this);

    }
    public void getcafs(){
        String url = "http://10.0.2.2:5000/cafeteria";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    int i=0;
                    while(i<11) {
                        JSONObject obj = response.getJSONObject(i);
                        String Name = obj.getString("CafName");
                        Log.d("mohammadkadoumi","name: "+Name);
                        String Owner = obj.getString("CafOwner");
                        Cafs.add(new item(Name,Owner));
                        i++;
                    }
                    recyclerView=findViewById(R.id.cafkistrec);// todo: add setupviews method
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(CafList.this));


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

        // Add the request to the request queue
        queue.add(request);
    }


    @Override
    public void onCafeteriaClick(int position) {

        Intent intent=new Intent(CafList.this,CommerceCafeteriaActivity.class);
        int x=position+1;
        intent.putExtra("pos",""+x);
        startActivity(intent);

    }
}