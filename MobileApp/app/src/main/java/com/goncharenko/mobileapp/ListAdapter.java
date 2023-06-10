package com.goncharenko.mobileapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{
    ArrayList<Model> modelsList;
    Context context;

    public ListAdapter(ArrayList<Model> songsList, Context context) {
        this.modelsList = songsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.node_item, parent,false);
        return new ListAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, int position) {
        Model model = modelsList.get(position);
        holder.id.setText("" + model.getId());
        holder.from.setText("" + model.getFrom());
        holder.to.setText("" + model.getTo());
        holder.amount.setText("" + model.getAmount());
        holder.result.setText("" + model.getResult());
    }

    @Override
    public int getItemCount() {
        return modelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView id;
        TextView from;
        TextView to;
        TextView amount;
        TextView result;
        public ViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            from = itemView.findViewById(R.id.from);
            to = itemView.findViewById(R.id.to);
            amount = itemView.findViewById(R.id.amount);
            result = itemView.findViewById(R.id.result);
        }
    }
}
