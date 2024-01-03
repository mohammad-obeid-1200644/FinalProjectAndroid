package com.example.finalprojectandroid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterRec extends RecyclerView.Adapter<MyViewHolder> implements View.OnClickListener {
    Context context;
    List<item> cafs;

    public AdapterRec(Context context, List<item> cafs) {
        this.context = context;
        this.cafs = cafs;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recdesign,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        item currentItem = cafs.get(position);
        holder.CafName.setText(cafs.get(position).getName());
        holder.CafDesc.setText(cafs.get(position).getOwner());
        holder.itemView.setOnClickListener(this);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return cafs.size();
    }

    public void onClick(View v) {
        int position = (int) v.getTag();

        item clickedItem = cafs.get(position);

//        Intent intent =new Intent(CafList.class, LoginActivity.class);

        Toast.makeText(context, "Clicked item: " + clickedItem.getName(), Toast.LENGTH_SHORT).show();

    }

}




