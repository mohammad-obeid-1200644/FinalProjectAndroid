package com.example.finalprojectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    EditText edtName, edtPassword;
    CheckBox chkRememberMe;
    private RequestQueue queue;
    ArrayList<User> users;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private static final String NAME = "MyPrefsFile";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageButton googlebtn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setUpViews();
        setupSharedPrefs();

        loadUserFromPreferences();

        googlebtn = findViewById(R.id.googlebtn);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
            navigateToSecondActivity();
        }
        googlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();

            }
        });
    }

    void signIn(){
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }

    }

    void navigateToSecondActivity(){
        finish();
        Intent intent = new Intent(LoginActivity.this,CafList.class);
        startActivity(intent);
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
        if (isEmpty(name) || isEmpty(password)){
            Toast.makeText(LoginActivity.this, "Please Fill in all fields!", Toast.LENGTH_LONG).show();
            return;
        }

        String url = "http://10.0.2.2:5000/user";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                users = new ArrayList<>();

                if (response != null) {
                    boolean userExists = false;

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            String dataBaseName = obj.getString("Name");
                            String dataBasePassword = obj.getString("Password");
                            String id=obj.getString("ID");


                            if (dataBaseName.equals(name) && dataBasePassword.equals(password)){
                                userExists = true;

                                if (chkRememberMe.isChecked()) {
                                    saveUserToPreferences(name, password);
                                }
                                Intent intent=new Intent(LoginActivity.this, CafList.class);
                                intent.putExtra("LoggedinUserID",id);
                                startActivity(intent);
                            }
                        } catch (JSONException exception) {
                            Log.d("Error", exception.toString());
                        }
                    } if (!userExists) {
                        Toast.makeText(LoginActivity.this, "This user does not exist!", Toast.LENGTH_LONG).show();
                    }
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

    public void setupSharedPrefs() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
    }
    private void saveUserToPreferences(String username, String password) {
        editor.putString(USERNAME, username);
        editor.putString(PASSWORD, password);
        editor.commit();
    }
    private void loadUserFromPreferences() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedUsername = preferences.getString(USERNAME, "");
        String savedPassword = preferences.getString(PASSWORD, "");

        if (savedUsername != null && savedPassword != null) {
            edtName.setText(savedUsername);
            edtPassword.setText(savedPassword);
            chkRememberMe.setChecked(true);
        }
    }

}