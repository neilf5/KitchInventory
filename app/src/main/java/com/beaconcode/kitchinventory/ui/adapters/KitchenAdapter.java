package com.beaconcode.kitchinventory.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beaconcode.kitchinventory.R;
import com.beaconcode.kitchinventory.data.database.entities.Kitchen;

import java.util.ArrayList;

public class KitchenAdapter extends RecyclerView.Adapter<KitchenAdapter.MyViewHolder> {
    Context context;
    ArrayList<Kitchen> foodList;


    public KitchenAdapter (Context context, ArrayList<Kitchen> foodList) {
        this.context = context;
        this.foodList = foodList;

    }

    @NonNull
    @Override
    public KitchenAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.food_row, parent, false);

        return new KitchenAdapter.MyViewHolder(view);
    }

    /*

     */
    @Override
    public void onBindViewHolder(@NonNull KitchenAdapter.MyViewHolder holder, int position) {
        holder.foodName.setText(foodList.get(position).getName());
        String temp = "" + foodList.get(position).getQuantity();
        holder.quantityName.setText(temp);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView foodName;
        TextView quantityName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            foodName = itemView.findViewById(R.id.foodName);
            quantityName = itemView.findViewById(R.id.totalQuantity);

        }
    }
}
