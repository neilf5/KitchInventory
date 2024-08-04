package com.beaconcode.kitchinventory.ui.adapters;

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
import com.beaconcode.kitchinventory.data.model.Meal;
import com.bumptech.glide.Glide;

/**
 * Adapter class for displaying a list of recipes in a RecyclerView.
 * This adapter uses DiffUtil to efficiently update the list when data changes.
 */
public class RecipesAdapter extends ListAdapter<Meal, RecipesAdapter.MyViewHolder> {

    Context context;

    /**
     * Constructor for RecipesAdapter.
     * Overrides the default DiffUtil.ItemCallback to compare Meal objects.
     * @param context The context in which the adapter is used.
     */
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

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recipes, parent, false);
        return new MyViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Meal meal = getItem(position);
        holder.recipeName.setText(meal.getStrMeal());
        Glide.with(context).load(meal.getStrMealThumb()).into(holder.recipeImage);
    }


    /**
     * ViewHolder class for RecipesAdapter.
     * This class holds the views for each item in the RecyclerView.
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView recipeImage;
        TextView recipeName;

        /**
         * Constructor for MyViewHolder.
         * @param itemView The view of the item.
         */
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeImage = itemView.findViewById(R.id.iv_recipeImage);
            recipeName = itemView.findViewById(R.id.tv_recipeName);

        }
    }
}

