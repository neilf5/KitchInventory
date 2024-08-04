package com.beaconcode.kitchinventory.views;

/**
 * Interface for handling item click events in a RecyclerView.
 * Implement this interface to define the action to be taken when an item is clicked.
 */
public interface CookInterface {
    /**
     * Called when an item in the RecyclerView is clicked.
     * @param position The position of the clicked item in the adapter.
     */
    void onItemClick(int position);
}
