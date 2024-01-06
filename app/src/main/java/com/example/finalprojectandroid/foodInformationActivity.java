package com.example.finalprojectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class foodInformationActivity extends AppCompatActivity {

    Button btnDecrement,btnIncrement;
    TextView txtQuantity;
    int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_information);
        btnDecrement = findViewById(R.id.btnDecrement);
        btnIncrement = findViewById(R.id.btnIncrement);
        txtQuantity = findViewById(R.id.txtQuantity);
    }

    public void incrementOnClick(View view){
        count++;
        txtQuantity.setText(""+count);

    }

    public void decrementOnClick(View view){
        if(count<=1)
            count=1;
        else
            count--;
        txtQuantity.setText(""+count);
    }

    public void backtolistclk(View view) {
        Intent inte = new Intent(foodInformationActivity.this, CommerceCafeteriaActivity.class);
        startActivity(inte);
    }

    public void AddToCartClk(View view) {
        Intent inte = new Intent(foodInformationActivity.this, CartActivity.class);
        startActivity(inte);
    }
}