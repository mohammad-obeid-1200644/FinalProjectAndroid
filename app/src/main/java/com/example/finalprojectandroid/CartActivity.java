package com.example.finalprojectandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import java.util.Iterator;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CardItemRecyclerAdapter.OnItemDeleteListener {
    private static RequestQueue queue;

    private RecyclerView recyclerView;
    int count = 1;
    private TextView totalPricetxt;
    private List<CartItem> items = new ArrayList<CartItem>();
    private CardItemRecyclerAdapter adapter;

    private RelativeLayout itemsLayout;
    private Button btnDecrement, btnIncrement;
    private ImageView CartItemImage;
    private TextView CartResturantName, CartFoodItemName, CartItemPrice, CartItemQuantity;
    private RelativeLayout relativeLayout;
    Button orderbtn;
    private static double totprice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        queue = Volley.newRequestQueue(this);
        setupviews();
        getitems();
        adapter = new CardItemRecyclerAdapter(CartActivity.this, items, this);


    }

    public void setupviews() {
        CartItemImage = findViewById(R.id.itemImg);
        CartResturantName = findViewById(R.id.CafName);
        CartFoodItemName = findViewById(R.id.txtItemName);
        CartItemPrice = findViewById(R.id.txtFoodPrice);
        CartItemQuantity = findViewById(R.id.txtQuantity1);
        recyclerView = findViewById(R.id.cafkistrec);// todo: add setupviews method
        relativeLayout = findViewById(R.id.rl1);
        totalPricetxt = findViewById(R.id.totalpricetxt);
        orderbtn=findViewById(R.id.orderbtn);
    }


    public void getitems() {
        String url = "http://10.0.2.2:5000/cartitem/1";
        Log.d("URL_Request", url);
        Intent intg = getIntent();
        String c = intg.getStringExtra("CafeteriaName");
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
//                    Log.d("dllamdllla","ddmdmmdmdmd");

                    for (int i = 0; i < response.length(); i++) {
//                        Log.d("ddsaasdds","ddddd");
                        JSONObject obj = response.getJSONObject(i);
//                        Log.d("ddsaasdds",obj.toString());
                        int id = obj.getInt("ID");

                        String name = obj.getString("Name");
                        String cafname = obj.getString("CafName");
                        int quan = obj.getInt("Quantity");
                        Log.d("quantitytest", quan + "");
                        double price = obj.getDouble("Total Price");
                        totprice += price;
                        String extras = obj.getString("extras");
                        items.add(new CartItem(id, cafname, name, price, quan, R.drawable.sandwiches));
                    }

                    totalPricetxt.setText(String.valueOf(totprice));


                    recyclerView = findViewById(R.id.CartItemRec);
                    recyclerView.setHasFixedSize(true);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setAdapter(adapter);
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


    private void remove_from_cart(int id) {
        String url = "http://10.0.2.2:5000/deleteitem/" + (id);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VolleyError", error.toString());
                    }
                }
        );
        queue.add(request);

        String url1 = "http://10.0.2.2:5000/cartitem/1";
        JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, url1,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    totprice = 0;
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject obj = response.getJSONObject(i);
                        double price = obj.getDouble("Total Price");
                        totprice += price;
                    }
                    totalPricetxt.setText(String.valueOf(totprice));
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
        queue.add(request1);
    }

    @Override
    public void onDeleteClick(int position) {
        int x = items.get(position).getId();
        items.remove(position);
        remove_from_cart(x);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, adapter.getItemCount());
//        relativeLayout.requestLayout();

    }


    public void ordernowClick(View view) {
        add_order(totprice, 1);
        getlastinsertedorderID();

        Iterator<CartItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            CartItem item = iterator.next();
            iterator.remove();
        }

        adapter.notifyDataSetChanged();

        totprice = 0;
        totalPricetxt.setText(String.valueOf(totprice));
        orderbtn.setVisibility(View.GONE);


    }


//    public void incrementOnClickcc(View view){
//        count+=1;
//        CartItemQuantity.setText(""+count);
//    }
//
//    public void decrementOnClickcc(View view){
//        if(count<=1)
//            count=1;
//        else
//            count-=1;
//        CartItemQuantity.setText(""+count);
//    }


    private void add_order(double TotPrice, int userID) {
        String url = "http://10.0.2.2:5000/createorder";
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("TotalPrice", TotPrice);
            jsonParams.put("UserID", userID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VolleyError", error.toString());
                    }

                }
        );
        queue.add(request);
    }

    private void getlastinsertedorderID() {
        String url1 = "http://10.0.2.2:5000/lastorderid";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url1,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int id = response.getInt("MAX(OrderID)");
                    Log.d("MohammadKadoumiTest", id + "");

                    String url1 = "http://10.0.2.2:5000/cartitem/1";
                    JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url1,
                            null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response1) {
                            try {
                                for (int i = 0; i < response1.length(); i++) {
                                    JSONObject obj = response1.getJSONObject(i);
                                    String price=obj.getString("Total Price");
                                    String cfname=obj.getString("CafName");
                                    Log.d("jdjcnlssd","id::::"+id+"    //"+price+","+cfname+" ==cfname");


                                    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                    String url = "http://10.0.2.2:5000/addorderitems";
                                    JSONObject jsonParams1 = new JSONObject();
                                    try {
                                        jsonParams1.put("OrderID", (id+1));
                                        jsonParams1.put("Price", price);
                                        jsonParams1.put("CafName", cfname);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    JsonObjectRequest request1 = new JsonObjectRequest(
                                            Request.Method.POST,
                                            url,
                                            jsonParams1,
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    remove_cartItems(1);
                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Log.d("VolleyError", error.toString());
                                                }

                                            }
                                    );
                                    queue.add(request1);
                                    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                }
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



    private void remove_cartItems(int id) {
        String url = "http://10.0.2.2:5000/deletecartitem/1";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VolleyError", error.toString());
                    }
                }
        );
        queue.add(request);
    }
}