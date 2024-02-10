package com.example.finalprojectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.text.SimpleDateFormat;


import org.json.JSONException;
import org.json.JSONObject;

public class UserActivity extends AppCompatActivity {
    String userId="";
    TextView edtUsername, edtEmail, edtPassword;
    User user;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Intent intent = getIntent();

        if (intent != null) {
            userId = intent.getStringExtra("LoggedinUserID");
            Log.d("loggedInUserId", userId);
        }

        setUpViews();
        loadUserInfo();

    }

    public void setUpViews(){
        queue = Volley.newRequestQueue(this);
        edtUsername= findViewById(R.id.edtUsername);
        edtEmail= findViewById(R.id.edtEmail);
        edtPassword= findViewById(R.id.edtPassword);
    }

    public void loadUserInfo(){
        String url = "http://10.0.2.2:5000/user/" + userId;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    user = new User();
                    user.setName(response.getString("Name"));
                    user.setEmail(response.getString("Email"));
                    user.setPass(response.getString("Password"));

                    edtUsername.setText(user.getName());
                    edtEmail.setText(user.getEmail());
                    edtPassword.setText(user.getPass());

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(UserActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Error_json", error.toString());
            }
        });

        queue.add(request);
    }

    public void saveOnClick(View view) {
        String url = "http://10.0.2.2:5000/update";

        JSONObject requestData = new JSONObject();
        try {
            requestData.put("ID", userId);
            requestData.put("Name", edtUsername.getText());
            requestData.put("Email", edtEmail.getText());
            requestData.put("Password", edtPassword.getText());
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(UserActivity.this, "User updated successfully!", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("Error_json", error.toString());
                    }
                });

        queue.add(request);
    }

    public void logOutOnClk(View view){
        Intent intent = new Intent(UserActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void deleteOnClk(View view) {

        String url = "http://10.0.2.2:5000/delete/" + userId;

        StringRequest request = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(UserActivity.this, "User deleted successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("Error_json", error.toString());
                    }
                });

        queue.add(request);
    }


    public void backOnClk3(View view) {
        Intent intent=new Intent(UserActivity.this, CafList.class);
        Intent ine = getIntent();
        String la = ine.getStringExtra("LoggedinUserID");
        intent.putExtra("LoggedinUserID",la);
        startActivity(intent);
    }

    public void homeOnClk3(View view) {
        Intent intent=new Intent(UserActivity.this, CafList.class);
        Intent ine = getIntent();
        String la = ine.getStringExtra("LoggedinUserID");
        intent.putExtra("LoggedinUserID",la);
        startActivity(intent);
    }

    public void cartOnClk3(View view) {
        Intent intent=new Intent(UserActivity.this, CartActivity.class);
        Intent ine = getIntent();
        String la = ine.getStringExtra("LoggedinUserID");
        intent.putExtra("LoggedinUserID",la);
        startActivity(intent);
    }

    public void userOnClk3(View view) {

    }
}