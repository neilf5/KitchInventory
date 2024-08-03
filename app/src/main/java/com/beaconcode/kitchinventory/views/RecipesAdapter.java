package com.beaconcode.kitchinventory.views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beaconcode.kitchinventory.R;
import com.beaconcode.kitchinventory.Recipe;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.MyViewHolder> {

    Context context;
    List<Recipe> recipeList;

    public RecipesAdapter(Context context, List<Recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public RecipesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesAdapter.MyViewHolder holder, int position) {
        holder.recipeName.setText(recipeList.get(position).strMeal);
        Glide.with(this.context).load(recipeList.get(position).strMealThumb).into(holder.recipeImage);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView recipeImage;
        TextView recipeName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            recipeImage = itemView.findViewById(R.id.iv_recipeImage);
            recipeName = itemView.findViewById(R.id.tv_recipeName);

        }
    }
}

