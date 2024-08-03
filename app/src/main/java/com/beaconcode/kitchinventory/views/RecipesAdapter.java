package com.beaconcode.kitchinventory.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beaconcode.kitchinventory.R;
import com.beaconcode.kitchinventory.Meal;
import com.bumptech.glide.Glide;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.MyViewHolder> {

    Context context;
    List<Meal> mealList;

    public RecipesAdapter(Context context, List<Meal> mealList) {
        this.context = context;
        this.mealList = mealList;
    }

    @NonNull
    @Override
    public RecipesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recipes, parent, false);
        return new RecipesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesAdapter.MyViewHolder holder, int position) {
        holder.recipeName.setText(mealList.get(position).getStrMeal());
        Glide.with(this.context).load(mealList.get(position).getStrMealThumb()).into(holder.recipeImage);
    }

    @Override
    public int getItemCount() {
        return mealList.size();
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

