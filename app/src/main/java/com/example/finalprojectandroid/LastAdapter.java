package com.example.finalprojectandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LastAdapter extends RecyclerView.Adapter<LastAdapter.MyViewHolder> {
    Context context;
    List<OrderInformation> OrderInfo;

    public LastAdapter(Context context, List<OrderInformation> OrderInfo) {
        this.context = context;
        this.OrderInfo = OrderInfo;
    }




    @NonNull
    @Override
    public LastAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order_info,parent,false));
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.CafName.setText(OrderInfo.get(position).getCafName());
        holder.foodname.setText(OrderInfo.get(position).getFoodname());
        holder.quantity.setText(String.valueOf(OrderInfo.get(position).getQuantity()));
        holder.Price.setText( String.valueOf(OrderInfo.get(position).getPrice()));
        holder.itemImg.setImageResource(OrderInfo.get(position).getImageID());



    }

    @Override
    public int getItemCount() {
        return OrderInfo.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView CafName,foodname,Price,quantity;
        ImageView itemImg;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            CafName=itemView.findViewById(R.id.CafName);
            foodname=itemView.findViewById(R.id.foodname);
            quantity=itemView.findViewById(R.id.quantity);
            Price=itemView.findViewById(R.id.Price);
            itemImg= itemView.findViewById(R.id.itemImg);
        }
    }


}



