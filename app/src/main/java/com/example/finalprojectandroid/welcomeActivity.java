package com.example.finalprojectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class welcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

//        Log.d("imgNum", R.drawable.p1p51p124+"");
//        Log.d("imgNum", R.drawable.p2p233p242+"");
//        Log.d("imgNum", R.drawable.p3p92p125+"");
//        Log.d("imgNum", R.drawable.p4+"");
//        Log.d("imgNum", R.drawable.p5+"");
//        Log.d("imgNum", R.drawable.p6+"");
//        Log.d("imgNum", R.drawable.p7+"");
//        Log.d("imgNum", R.drawable.p8+"");
//        Log.d("imgNum", R.drawable.p9p88p192+"");
//        Log.d("imgNum", R.drawable.p10p209+"");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(welcomeActivity.this,LoginActivity.class));
            }
        },2000);

    }


}