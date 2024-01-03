package com.example.finalprojectandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class CafList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<item> Cafs=new ArrayList<item>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caf_list);
        Cafs.add(new item("Commerce","Jammal Naser"));
        Cafs.add(new item("Science","Jammal Naser"));
        Cafs.add(new item("Literature","Rifaq For Trading & Investment Company"));
        Cafs.add(new item("Meramie Cafe","Food Cort"));
        Cafs.add(new item("Top Shawerma","Food Cort"));
        Cafs.add(new item("Ranoosh cocktail ","Food Cort"));
        Cafs.add(new item("Nutella","Food Cort"));
        Cafs.add(new item("Toast","Food Cort"));
        Cafs.add(new item("Burger","Food Cort"));
        Cafs.add(new item("Grand Falafel","Abbas Alaloul"));
        Cafs.add(new item("Aldom",""));
        recyclerView=findViewById(R.id.cafkistrec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AdapterRec(getApplicationContext(),Cafs));

    }

}