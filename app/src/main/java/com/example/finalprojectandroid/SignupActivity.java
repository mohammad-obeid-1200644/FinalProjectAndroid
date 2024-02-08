package com.example.finalprojectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SignupActivity extends AppCompatActivity {
    private EditText edtName, edtEmail, edtPassword, edtRepeatPassword;
    private RequestQueue queue;
    ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        setUpViews();
    }

    public void setUpViews(){
        queue = Volley.newRequestQueue(this);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtRepeatPassword = findViewById(R.id.edtRepeatPassword);
    }

    public void signUpOnClk(View view) {
        Intent intent=new Intent(SignupActivity.this, CafList.class);
        String userName = edtName.getText().toString();
        String userEmail = edtEmail.getText().toString();
        String userPassword = edtPassword.getText().toString();
        String repeatedPassword = edtRepeatPassword.getText().toString();


        if (!isEmpty(userName) && !isEmpty(userEmail) && !isEmpty(userPassword) && !isEmpty(repeatedPassword)) {
            if (isValidEmail(userEmail) && userPassword.equals(repeatedPassword)) {
                if (isValidPassword(userPassword)) {
                    //read the user table from database to make sure that there is no two same users
                    String url = "http://10.0.2.2:5000/user";
                    JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                            null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            users = new ArrayList<>();
                            boolean userFound = false;

                            if (response != null) {
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        JSONObject obj = response.getJSONObject(i);
                                        User user = new User(
                                                obj.getString("Name"),
                                                obj.getString("Email"),
                                                obj.getString("Password")
                                        );
                                        users.add(user);
                                    } catch (JSONException exception) {
                                        Log.d("Error", exception.toString());
                                    }
                                }

                                for (int i = 0; i < users.size(); i++) {
                                    User user = users.get(i);
                                    User userToAdd = new User(userName, userEmail, userPassword);
                                    if (user.equals(userToAdd)) {
                                        userFound = true;
                                        Toast.makeText(SignupActivity.this, "User Exists!", Toast.LENGTH_LONG).show();
                                        break;
                                    }
                                }
                            }else {
                                Toast.makeText(SignupActivity.this, "No Users in the database!", Toast.LENGTH_LONG).show();
                                Log.d("Error_json", "Null response from the server.");
                            }

                            if (!userFound) {
                                addUser(userName, userEmail, userPassword);
                                String url1 = "http://10.0.2.2:5000/lastuserinserted";
                                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url1,
                                        null, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            int id = response.getInt("MAX(ID)")+1;
                                            intent.putExtra("LoggedinUserID",id+"");
                                            startActivity(intent);
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
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(SignupActivity.this, error.toString(),
                                    Toast.LENGTH_SHORT).show();
                            Log.d("Error_json", error.toString());
                        }
                    });

                    queue.add(request);

                } else {
                    Toast.makeText(SignupActivity.this, "Password must be at least 8 characters long, including numbers, and contain at least one character.", Toast.LENGTH_LONG).show();
                }
            } else {
                if (!isValidEmail(userEmail)) {
                    Toast.makeText(SignupActivity.this, "Invalid email address.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SignupActivity.this, "Passwords do not match.", Toast.LENGTH_LONG).show();
                    edtPassword.setText("");
                    edtRepeatPassword.setText("");
                }
            }
        }else {
            Toast.makeText(SignupActivity.this, "Please fill in all fields.", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        return password.matches(passwordPattern);
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
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

//    private boolean isValidPassword(String password) {
//        if (password.length() < 8) {
//            return false;
//        }
//        boolean containsAlphabetic = false;
//        for (char c : password.toCharArray()) {
//            if (Character.isLetter(c)) {
//                containsAlphabetic = true;
//                break;
//            }
//        }
//        boolean containsDigit = false;
//        for (char c : password.toCharArray()) {
//            if (Character.isDigit(c)) {
//                containsDigit = true;
//                break;
//            }
//        }
//        return containsAlphabetic && containsDigit;
//    }
}