package com.beaconcode.kitchinventory.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beaconcode.kitchinventory.R;

import java.util.ArrayList;

public class CookAdapter extends RecyclerView.Adapter<CookAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> foodList;

    public CookAdapter(Context context, ArrayList<String> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public CookAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_cook, parent, false);
        return new CookAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CookAdapter.MyViewHolder holder, int position) {
        holder.foodName.setText(foodList.get(position));

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView foodName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.tv_foodName);
        }
    }
}
