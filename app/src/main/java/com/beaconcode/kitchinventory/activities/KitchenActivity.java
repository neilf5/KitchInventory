package com.beaconcode.kitchinventory.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.recyclerview.widget.RecyclerView;

import com.beaconcode.kitchinventory.R;
import com.beaconcode.kitchinventory.data.database.KitchenRepository;
import com.beaconcode.kitchinventory.databinding.ActivityKitchenBinding;


import java.util.ArrayList;


/**
 * This activity will display the current items in the user's kitchen inventory from the database.
 */
public class KitchenActivity extends BaseActivity {

    private ArrayList<String> kitchenInventory = new ArrayList<>();
    private ActivityKitchenBinding binding;
    private KitchenRepository kitchenRepository;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKitchenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.kitchenDisplayRecyclerView;

        kitchenRepository = KitchenRepository.getRepository(getApplication());
    }

    /**
     * Initializes the contents of the Activity's standard options menu.
     * @param menu The options menu in which you place your items.
     * @return true for the menu to be displayed; if false, it will not be shown.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    /**
     * Factory method to create an Intent for starting KitchenActivity.
     * @param context The context from which the activity is started.
     * @return An Intent to start KitchenActivity.
     */
    static Intent kitchenActivityIntentFactory(Context context) {
        return new Intent(context, KitchenActivity.class);
    }
}