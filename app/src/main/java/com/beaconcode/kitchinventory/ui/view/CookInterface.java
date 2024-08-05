package com.beaconcode.kitchinventory.ui.view;

/**
 * Interface for handling item click events in a RecyclerView.
 * Implement this interface to define the action to be taken when an item is clicked.
 */
public interface CookInterface {
    /**
     * Called when an item in the RecyclerView is clicked.
     * @param foodName The name of the food item that was clicked.
     */
    void onItemClick(String foodName);
}
