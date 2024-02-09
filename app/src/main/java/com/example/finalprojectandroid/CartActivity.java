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
import java.util.Properties;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


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
    private static String mailsubject="";

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
        totprice=0;
        Intent hhint=getIntent();
        int la = Integer.parseInt(hhint.getStringExtra("LoggedinUserID"));
        String url = "http://10.0.2.2:5000/cartitem/"+la;
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
                        int imgID=obj.getInt("imgID");
                        items.add(new CartItem(id, cafname, name, price, quan, imgID));
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
        totprice = 0;
        String url = "http://10.0.2.2:5000/deleteitem/" + (id);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        totprice=0;
                        Intent hhint=getIntent();
                        int la = Integer.parseInt(hhint.getStringExtra("LoggedinUserID"));
                        String url1 = "http://10.0.2.2:5000/cartitem/"+la;
                        JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, url1,
                                null, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
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
                                totalPricetxt.setText("0");
                                Log.e("Network_Error", error.toString());
                            }

                        });
                        queue.add(request1);
//                        request1
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

    @Override
    public void onDeleteClick(int position) {
        int x = items.get(position).getId();
        items.remove(position);
        remove_from_cart(x);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, adapter.getItemCount());
//        totalPricetxt.setText(String.valueOf(totprice));
//        relativeLayout.requestLayout();

    }


    public void ordernowClick(View view) {
        Intent hhint=getIntent();
        int la = Integer.parseInt(hhint.getStringExtra("LoggedinUserID"))+1;
        Intent lastint=new Intent(CartActivity.this,OrderActivity.class);
        int p=la;
        lastint.putExtra("LoggedinUserID",p+"");
        Log.d("jajajjajlslsla",la+"");
        add_order(totprice, la);
//
        Iterator<CartItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            CartItem item = iterator.next();
            iterator.remove();
        }

        adapter.notifyDataSetChanged();

        totprice = 0;
        totalPricetxt.setText(String.valueOf(totprice));
        orderbtn.setVisibility(View.GONE);
//        Log.d("skjbdkjdsjkn",mailsubject);

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
                    String url1 = "http://10.0.2.2:5000/cartitem/"+la;
                    JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url1,
                            null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response1) {
                            try {
                                for (int i = 0; i < response1.length(); i++) {
                                    JSONObject obj = response1.getJSONObject(i);
                                    String price=obj.getString("Total Price");
                                    String cfname=obj.getString("CafName");
                                    String FoodName=obj.getString("Name");
                                    String Quan=obj.getString("Quantity");
                                    int imgi=obj.getInt("imgID");
                                    Log.d("jdjcnlssd","id::::"+id+"    //"+price+","+cfname+" ==cfname");
                                    mailsubject+="Food Name: "+FoodName+", Quantity: "+Quan+", Total Price: "+
                                            price+", From Caf: "+cfname+"\n";
                                    addtovirtcart( FoodName, Integer.valueOf(Quan), Double.valueOf(price),cfname, la, imgi, id);
                                    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                    String url = "http://10.0.2.2:5000/addorderitems";
                                    JSONObject jsonParams1 = new JSONObject();
                                    try {
                                        jsonParams1.put("OrderID", (id));
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
                                                    Intent hhint=getIntent();
                                                    int la = Integer.parseInt(hhint.getStringExtra("LoggedinUserID"));
                                                    remove_cartItems(la);
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
                                    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                }
                                sendemail(mailsubject);
                                Log.d("skjbdkjdsjkn",mailsubject);
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
    private void addtovirtcart( String Name, int Quant, double TotPrice, String cafname, int CustID, int imgid, int orderid){
        String url = "http://10.0.2.2:5000/addcartitemm";

        RequestQueue queue = Volley.newRequestQueue(CartActivity.this);

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


    private void remove_cartItems(int id) {
        String url = "http://10.0.2.2:5000/deletecartitem/"+id;
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



    private void sendemail(String subj) {
        final String username = "mohammadkadoumi77@gmail.com"; // Your email
        final String password = "olmi vvcc kjwz rqen"; // Your email password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("mohammadkadoumi77@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("raghad.saleem.aqel@gmail.com"));//
            message.setSubject("New Order");
            message.setText(subj);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(message);
                        Log.d("ememememeee","Email Sent");
                    } catch (Exception e) {
                        Log.d("ememememeee","Can Not Send mail");
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}