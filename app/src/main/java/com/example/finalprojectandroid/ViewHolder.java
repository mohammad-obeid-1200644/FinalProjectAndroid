package com.example.finalprojectandroid;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView orderNumber_val,Totalprice_val;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        orderNumber_val= itemView.findViewById(R.id.orderNumber_val);
        Totalprice_val= itemView.findViewById(R.id.Totalprice_val);

    }
}