package com.example.finalprojectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;



public class SignupActivity extends AppCompatActivity {
    private EditText edtName, edtEmail, edtPassword, edtRepeatPassword;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        setUpViews();
    }

    public void setUpViews(){
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtRepeatPassword = findViewById(R.id.edtRepeatPassword);
    }

    public void signUpOnClk(View view) {
        String userName = edtName.getText().toString();
        String userEmail = edtEmail.getText().toString();
        String userPassword = edtPassword.getText().toString();

        addUser(userName, userEmail, userPassword);
    }

    private void addUser(String userName, String userEmail, String userPassword){
        String url = "http://10.0.2.2:5000/create";

        RequestQueue queue = Volley.newRequestQueue(SignupActivity.this);

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("Name", userName);
            jsonParams.put("Email", userEmail);
            jsonParams.put("Password", userPassword);
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
                        String str = "";
                        try {
                            str = response.getString("result");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(SignupActivity.this, str,
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
}