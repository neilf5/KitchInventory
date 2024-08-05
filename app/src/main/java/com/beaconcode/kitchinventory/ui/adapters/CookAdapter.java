package com.beaconcode.kitchinventory.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.beaconcode.kitchinventory.R;
import com.beaconcode.kitchinventory.ui.view.CookInterface;

import java.util.ArrayList;

/**
 * Adapter class for displaying a list of food items in a RecyclerView.
 * This adapter binds the data to the views in each item of the RecyclerView.
 */
public class CookAdapter extends ListAdapter<String, CookAdapter.MyViewHolder> {

    private final CookInterface cookInterface;
    Context context;

    /**
     * Constructor for CookAdapter.
     *
     * @param context       The context in which the adapter is used.
     * @param cookInterface The interface for handling item click events.
     */
    public CookAdapter(Context context, ArrayList<String> foodList, CookInterface cookInterface) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.cookInterface = cookInterface;
    }

    private static final DiffUtil.ItemCallback<String> DIFF_CALLBACK = new DiffUtil.ItemCallback<String>() {
        @Override
        public boolean areItemsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem.equals(newItem);
        }
    };

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public CookAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_cook, parent, false);
        return new CookAdapter.MyViewHolder(view, cookInterface);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull CookAdapter.MyViewHolder holder, int position) {
        String foodName = getItem(position);
        holder.foodName.setText(foodName);
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
                        cookInterface.onItemClick(foodName.getText().toString());
                    }
                }
            });
        }
    }
}
