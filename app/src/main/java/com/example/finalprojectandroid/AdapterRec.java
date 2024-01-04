package com.example.finalprojectandroid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterRec extends RecyclerView.Adapter<AdapterRec.MyViewHolder> {
   private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    List<item> cafs;

    public AdapterRec(Context context, List<item> cafs,RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.cafs = cafs;
        this.recyclerViewInterface=recyclerViewInterface;
    }

    @NonNull
    @Override
    public AdapterRec.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.recdesign,parent,false);
        return new AdapterRec.MyViewHolder(view,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRec.MyViewHolder holder, int position) {
        holder.nametxt.setText(cafs.get(position).getName());
        holder.ownertxt.setText(cafs.get(position).getOwner());
    }

    @Override
    public int getItemCount() {
        return cafs.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nametxt,ownertxt;
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            nametxt=itemView.findViewById(R.id.txtCafName);
            ownertxt=itemView.findViewById(R.id.txtCafDesc);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface!=null){
                        int position =getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            recyclerViewInterface.onCafeteriaClick(position);
                        }
                    }
                }
            });
        }
    }


}




