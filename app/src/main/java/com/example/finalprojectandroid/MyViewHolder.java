package com.example.finalprojectandroid;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
     TextView CafName,CafDesc;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        CafName=itemView.findViewById(R.id.txtItemName);
        CafDesc=itemView.findViewById(R.id.txtCafDesc);
    }
}
