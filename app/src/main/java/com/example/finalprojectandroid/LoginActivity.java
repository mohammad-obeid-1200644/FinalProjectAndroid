package com.example.finalprojectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {
    EditText edtName, edtPassword;
    CheckBox chkRememberMe;
    private RequestQueue queue;
    ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setUpViews();
    }

    public void setUpViews(){
        queue = Volley.newRequestQueue(this);
        edtName = findViewById(R.id.edtName);
        edtPassword = findViewById(R.id.edtPassword);
        chkRememberMe = findViewById(R.id.chkRememberMe);
    }

    public void signUpOnClk(View view) {
        Intent intent=new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
    }

    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public void logInOnClk(View view) {
        String name = edtName.getText().toString();
        String password = edtPassword.getText().toString();

        if (isEmpty(name) && isEmpty(password)){
            Toast.makeText(LoginActivity.this, "Please Fill in all fields!", Toast.LENGTH_LONG).show();
            return;
        }


        String url = "http://10.0.2.2:5000/user";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                users = new ArrayList<>();
               // boolean userFound = false;

                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            String dataBaseName = obj.getString("Name");
                            String dataBasePassword = obj.getString("Password");



                            if (dataBaseName.equals(name) && dataBasePassword.equals(password)){
                                Intent intent=new Intent(LoginActivity.this, CafList.class);
                                startActivity(intent);
                            }
                        } catch (JSONException exception) {
                            Log.d("Error", exception.toString());
                        }
                    }
                    Toast.makeText(LoginActivity.this, "This user does not exists!", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(LoginActivity.this, "No Users in the database!", Toast.LENGTH_LONG).show();
                    Log.d("Error_json", "Null response from the server.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(LoginActivity.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
                Log.d("Error_json", error.toString());
            }
        });

        queue.add(request);


    }
}