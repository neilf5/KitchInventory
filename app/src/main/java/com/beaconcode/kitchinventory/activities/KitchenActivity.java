package com.beaconcode.kitchinventory.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beaconcode.kitchinventory.R;
import com.beaconcode.kitchinventory.data.database.KitchenRepository;
import com.beaconcode.kitchinventory.data.database.entities.Kitchen;
import com.beaconcode.kitchinventory.databinding.ActivityKitchenBinding;
import com.beaconcode.kitchinventory.ui.adapters.KitchenAdapter;
import com.beaconcode.kitchinventory.ui.adapters.ShoppingListAdapter;


import java.util.ArrayList;
import java.util.List;


/**
 * This activity will display the current items in the user's kitchen inventory from the database.
 */
public class KitchenActivity extends BaseActivity {

    private ArrayList<String> kitchenList = new ArrayList<>();
    private ArrayList<Integer> quantityList = new ArrayList<>();
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

        recyclerViewSetup();



    }

    private void recyclerViewSetup(){
        setUpFoodList();
        getFoodList();

        KitchenAdapter adapter = new KitchenAdapter(KitchenActivity.this, kitchenList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(null));
    }


    private void getFoodList() {
        LiveData<List<String>> userObserver = kitchenRepository.getFoodList();
        userObserver.observe(this, list -> {
            this.kitchenList = (ArrayList<String>) list;
            if (list != null) {
                invalidateOptionsMenu();
            }
        });
    }

    private void getQuantityList() {
        LiveData<List<Integer>> userObserver = kitchenRepository.getQuantityList();
        userObserver.observe(this, list -> {
            this.quantityList = (ArrayList<Integer>) list;
            if (list != null) {
                invalidateOptionsMenu();
            }
        });
    }

    /**
     * Sets up the list of food items to be displayed in the RecyclerView.
     * Adds predefined food items to the foodList.
     * This method should be replaced with fetching data from a room database when possible.
     */
    private void setUpFoodList() {
        try {
            for (Kitchen food : kitchenRepository.getAllLogs()) {
                kitchenList.add(food.getName());
            }
        } catch (Exception e) {
            Toast.makeText(this, "Could not add to kitchen list", Toast.LENGTH_SHORT).show();
        }
    }

    private void setUpQuantityList() {
        try {
            for (Kitchen amount : kitchenRepository.getAllLogs()) {
                quantityList.add(amount.getQuantity());
            }
        } catch (Exception e) {
            Toast.makeText(this, "Could not add to quantity list", Toast.LENGTH_SHORT).show();
        }
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