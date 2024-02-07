package com.example.finalprojectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class welcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Log.d("picNum", R.drawable.p3+"");

    }

    public void logInOnClk1(View view) {
        Intent intent=new Intent(welcomeActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}