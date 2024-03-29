package com.example.finalprojectandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
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

public class foodInformationActivity extends AppCompatActivity {
    private static RequestQueue queue;

    Button btnDecrement,btnIncrement;
    TextView foodnametxt,foodpricetxt;
    CheckBox ex1,ex2,ex3;
    TextView txtQuantity;
    private String name="";
    private double price=0;
    private double u=price;
    int count = 1;
    private static int pos=0;
    private static int foodid=0;
    private int [] extras;
    private static final int flagg=0;
    private ImageView imgview;
    String cafename="";
    int imgID=0;
    int imgNum=0;
    private TextView extr;
    int Quant=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_information);
        extras=new int[3];
        queue = Volley.newRequestQueue(this);
        //#todo: add setupviews method
        btnDecrement = findViewById(R.id.btnDecrement);
        btnIncrement = findViewById(R.id.btnIncrement);
        txtQuantity = findViewById(R.id.txtQuantity);
        foodnametxt=findViewById(R.id.foodNameTxt);
        foodpricetxt=findViewById(R.id.price);
        imgview =findViewById(R.id.foodImage);
        ex1=findViewById(R.id.extraschkbox1);
        ex2=findViewById(R.id.extraschkbox2);
        ex3=findViewById(R.id.extraschkbox3);
        extr=findViewById(R.id.additionstxt);

        getfood();
        getextras();
        getcafName();
//        fillextras();

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

    public void getcafName(){
        Intent intent = getIntent();
        int pos = Integer.valueOf(intent.getStringExtra("Cafpos"));
//        Log.d("lallakkak",pos+"lllll");
        String url = "http://10.0.2.2:5000/cafeteria/"+pos;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String name = response.getString("CafName");
                    cafename=name;
                    Intent inten=new Intent(foodInformationActivity.this, CartActivity.class);
                    inten.putExtra("CafeteriaName",name);
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
                            foodid = response.getInt("FoodID");
                            imgID = response.getInt("FoodImg");
                            u=price;
                            foodnametxt.setText(name);
                            foodpricetxt.setText(String.valueOf(price));
                            imgNum = Integer.parseInt((response.getString("FoodImg")));
                            imgview.setImageResource(imgNum);

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




    public void getextras() {
        Intent intent = getIntent();
        int pos = Integer.valueOf(intent.getStringExtra("mohammad"));
        String url = "http://10.0.2.2:5000/foodextra/"+pos ;
        Log.d("URL_Request", url);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    //Log.d("checkkkk", response.toString());
                        for (int flagg = 0; flagg < response.length(); flagg++) {
                            JSONObject obj = response.getJSONObject(flagg);
                            int id = obj.getInt("ExtraID");
                            extras[flagg] = id;
                            String url = "http://10.0.2.2:5000/extra/" + id;
                            Log.d("URL_Request", url);
                            int finalFlagg = flagg;
                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                                    null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        String obj = response.getString("ExtraName");
                                        if (finalFlagg == 0) {
                                            extr.setVisibility(View.VISIBLE);
                                            ex1.setVisibility(View.VISIBLE);
                                            ex1.setText(obj);
                                        }
                                        if (finalFlagg == 1) {
                                            ex2.setVisibility(View.VISIBLE);
                                            ex2.setText(obj);
                                        }
                                        if (finalFlagg == 2) {
                                            ex3.setVisibility(View.VISIBLE);
                                            ex3.setText(obj);
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

                        }


                    } catch(JSONException e){
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


    private void add_to_cart( String Name, int Quant, double TotPrice,String extras,String cafname, int CustID, int imgid){
        String url = "http://10.0.2.2:5000/addcartitem";

        RequestQueue queue = Volley.newRequestQueue(foodInformationActivity.this);

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("Name", Name);
            jsonParams.put("Quantity", Quant);
            jsonParams.put("TotalPrice", TotPrice);
            jsonParams.put("extras", extras);
            jsonParams.put("customerID", CustID);
            jsonParams.put("CafName", cafname);
            jsonParams.put("imgID", imgid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("additeminfo","Name: "+ Name+"\nQuant: "+Quant+"\nTotPrice: "+TotPrice+"\nextrs: "+extras+"\ncustID: "+CustID+"\nCafName: "+cafename);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String str = "";
                        try {
                            str = response.getString("result");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//
                        Toast.makeText(foodInformationActivity.this, str,
                                Toast.LENGTH_SHORT).show();

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
    public void AddToCartClk(View view) {
        int x=Integer.valueOf(txtQuantity.getText().toString());
        String extras="";
        if(!ex1.getText().equals("")){

            if(ex1.isChecked())
                extras+=ex1.getText().toString();
        }
        if(!ex2.getText().equals("")){
            if(ex2.isChecked())
                if(ex1.isChecked())
                extras+=", "+ex2.getText().toString();
                else
                    extras+=ex2.getText().toString();

        }
        if(!ex3.getText().equals("")){
            if(ex3.isChecked())
                if(ex1.isChecked() || ex2.isChecked())
                    extras+=", "+ex3.getText().toString();
                else
                    extras+=ex3.getText().toString();
        }
        if(extras.equals(""))
            extras="No extras";
        Intent hhint=getIntent();
        String la = hhint.getStringExtra("LoggedinUserID");
        add_to_cart(name,x,price*x,extras,cafename,Integer.valueOf(la),imgID);
        Intent inte = new Intent(foodInformationActivity.this, CartActivity.class);
        inte.putExtra("LoggedinUserID",la);
        startActivity(inte);
    }
    public void backOnClks(View view) {
        Intent intent=new Intent(foodInformationActivity.this, CafList.class);
        Intent ine = getIntent();
        String la = ine.getStringExtra("LoggedinUserID");
        intent.putExtra("LoggedinUserID",la);
        startActivity(intent);
    }

    public void homeClicks(View view) {
        Intent intent=new Intent(foodInformationActivity.this, CafList.class);
        Intent ine = getIntent();
        String la = ine.getStringExtra("LoggedinUserID");
        intent.putExtra("LoggedinUserID",la);
        startActivity(intent);
    }



    public void userClkks(View view) {
        Intent intent=new Intent(foodInformationActivity.this, UserActivity.class);
        Intent ine = getIntent();
        String la = ine.getStringExtra("LoggedinUserID");
        intent.putExtra("LoggedinUserID",la);
        startActivity(intent);
    }
    public void BuyNowClk(View view) {
        Intent hhint=getIntent();
        int la = Integer.parseInt(hhint.getStringExtra("LoggedinUserID"));
        Intent lastint=new Intent(foodInformationActivity.this,OrderActivity.class);
        int p=la;
        lastint.putExtra("LoggedinUserID",p+"");
        Log.d("jajajjajlslsla",la+"");
        add_order(price, la);
        startActivity(lastint);
    }

    private void add_order(double TotPrice, int userID) {
        String url = "http://10.0.2.2:5000/createorder";
        JSONObject jsonParams = new JSONObject();
        try {
            Intent hhint=getIntent();
            int la = Integer.parseInt(hhint.getStringExtra("LoggedinUserID"));
            jsonParams.put("TotalPrice", TotPrice);
            jsonParams.put("UserID", la);
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
                        getlastinsertedorderID();
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
                    Intent hhint=getIntent();
                    int la = Integer.parseInt(hhint.getStringExtra("LoggedinUserID"));

                    add_order_item(id,price*count, cafename);

                    addtovirtcart( name, count, price*count, cafename, la, imgNum , id);
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

    public void add_order_item(int id,double pric,String cfname){
        String url = "http://10.0.2.2:5000/addorderitems";
        JSONObject jsonParams1 = new JSONObject();
        try {
            jsonParams1.put("OrderID", (id));
            jsonParams1.put("Price", pric);
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
                        Intent hhint=getIntent();
                        int la = Integer.parseInt(hhint.getStringExtra("LoggedinUserID"));
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

    }



    private void addtovirtcart( String Name, int Quant, double TotPrice, String cafname, int CustID, int imgid, int orderid){
        String url = "http://10.0.2.2:5000/addcartitemm";

        RequestQueue queue = Volley.newRequestQueue(foodInformationActivity.this);

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("Name", Name);
            jsonParams.put("Quantity", Quant);
            jsonParams.put("TotalPrice", TotPrice);
            jsonParams.put("customerID", CustID);
            jsonParams.put("CafName", cafname);
            jsonParams.put("imgID", imgid);
            jsonParams.put("OrderID", orderid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        Log.d("additeminfo","Name: "+ Name+"\nQuant: "+Quant+"\nTotPrice: "+TotPrice+"\nextrs: "+extras+"\ncustID: "+CustID+"\nCafName: "+cafename);

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


    public void cartOnClk1(View view) {

    }


    public void homeOnClk(View view) {
        Intent intent=new Intent(foodInformationActivity.this, CafList.class);
        Intent ine = getIntent();
        String la = ine.getStringExtra("LoggedinUserID");
        intent.putExtra("LoggedinUserID",la);
        startActivity(intent);
    }

    public void userClkk(View view) {
        Intent intent=new Intent(foodInformationActivity.this, UserActivity.class);
        Intent ine = getIntent();
        String la = ine.getStringExtra("LoggedinUserID");
        intent.putExtra("LoggedinUserID",la);
        startActivity(intent);
    }


}