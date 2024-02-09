package com.example.finalprojectandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    List<OrderItem> Order;
    public Adapter(RecyclerViewInterface recyclerViewInterface, Context context, List<OrderItem> order) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.Order = order;
    }


    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.item_order,parent,false);
        return new Adapter.ViewHolder(view,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        holder.orderNumber_val.setText(String.valueOf(Order.get(position).getNumberoforder()));
        holder.Totalprice_val.setText(String.valueOf((int) Order.get(position).getTotalprice()));
    }


    @Override
    public int getItemCount() {
        return Order.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView orderNumber_val,Totalprice_val;
        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            orderNumber_val= itemView.findViewById(R.id.orderNumber_val);
            Totalprice_val= itemView.findViewById(R.id.Totalprice_val);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            recyclerViewInterface.onCafeteriaClick(position);
                        }
                    }
                }
            });
        }
    }

}
