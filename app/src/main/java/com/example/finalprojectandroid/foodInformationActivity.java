package com.example.finalprojectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class foodInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_information);
    }

    public void backtolistclk(View view) {
        Intent inte = new Intent(foodInformationActivity.this, CommerceCafeteriaActivity.class);
        startActivity(inte);
    }
}