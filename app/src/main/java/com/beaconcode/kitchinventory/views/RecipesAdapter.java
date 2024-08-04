package com.beaconcode.kitchinventory.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.beaconcode.kitchinventory.R;
import com.beaconcode.kitchinventory.Meal;
import com.bumptech.glide.Glide;

import java.util.List;

public class RecipesAdapter extends ListAdapter<Meal, RecipesAdapter.MyViewHolder> {

    Context context;

    public RecipesAdapter(Context context) {
        super(new DiffUtil.ItemCallback<Meal>() {
            @Override
            public boolean areItemsTheSame(@NonNull Meal oldItem, @NonNull Meal newItem) {
                return oldItem.getIdMeal().equals(newItem.getIdMeal());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Meal oldItem, @NonNull Meal newItem) {
                return oldItem.equals(newItem);
            }
        });
        this.context = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recipes, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Meal meal = getItem(position);
        holder.recipeName.setText(meal.getStrMeal());
        Glide.with(context).load(meal.getStrMealThumb()).into(holder.recipeImage);
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

