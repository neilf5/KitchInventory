package com.beaconcode.kitchinventory.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.beaconcode.kitchinventory.R;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {
    private List<String> ingredients;
    private List<String> measures;
    Context context;

    public IngredientsAdapter(List<String> ingredients, List<String> measures) {
        this.ingredients = ingredients;
        this.measures = measures;
    }

    public void updateData(List<String> ingredients, List<String> measures) {
        this.ingredients = ingredients;
        this.measures = measures;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredients, parent, false);
        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        holder.tvMeasure.setText(measures.get(position));
        holder.tvIngredient.setText(ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public static class IngredientsViewHolder extends RecyclerView.ViewHolder {
        TextView tvMeasure;
        TextView tvIngredient;

        public IngredientsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMeasure = itemView.findViewById(R.id.tv_measure);
            tvIngredient = itemView.findViewById(R.id.tv_ingredient);
        }
    }
}
