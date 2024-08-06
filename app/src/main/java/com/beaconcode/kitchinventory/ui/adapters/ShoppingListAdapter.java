package com.beaconcode.kitchinventory.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beaconcode.kitchinventory.R;
import com.beaconcode.kitchinventory.ui.view.CookInterface;

import java.util.ArrayList;

/**
 * Adapter class for displaying a list of food items in a RecyclerView.
 * This adapter binds the data to the views in each item of the RecyclerView.
 */
public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.MyViewHolder> {

    private final CookInterface cookInterface;
    Context context;
    ArrayList<String> foodList;

    /**
     * Constructor for CookAdapter.
     * @param context The context in which the adapter is used.
     * @param foodList The list of food items to be displayed.
     * @param cookInterface The interface for handling item click events.
     */
    public ShoppingListAdapter(Context context, ArrayList<String> foodList, CookInterface cookInterface) {
        this.context = context;
        this.foodList = foodList;
        this.cookInterface = cookInterface;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public ShoppingListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_cook, parent, false);
        return new ShoppingListAdapter.MyViewHolder(view, cookInterface);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.foodName.setText(foodList.get(position));

    }


    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return foodList.size();
    }

    /**
     * ViewHolder class for CookAdapter.
     * This class holds the views for each item in the RecyclerView.
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView foodName;

        /**
         * Constructor for MyViewHolder.
         * @param itemView The view of the item.
         * @param cookInterface The interface for handling item click events.
         */
        public MyViewHolder(@NonNull View itemView, CookInterface cookInterface) {
            super(itemView);
            foodName = itemView.findViewById(R.id.tv_foodName);

            itemView.setOnClickListener(v -> {
                if (cookInterface != null) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        cookInterface.onItemClick(position);
                    }
                }
            });
        }
    }
}