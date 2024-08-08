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


import java.util.ArrayList;
import java.util.List;


/**
 * This activity will display the current items in the user's kitchen inventory from the database.
 */


public class KitchenActivity extends BaseActivity {

    private ArrayList<Kitchen> kitchenList = new ArrayList<>();
    private ArrayList<String> foodList = new ArrayList<>();
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

        binding.kitchenDeleteButton.setOnClickListener(v -> {
            Intent intent = KitchenDelete.kitchenDeleteActivitiyIntentFactory(getApplicationContext());
            startActivity(intent);
        });

        binding.kitchenGoHomeButton.setOnClickListener(v -> {
            Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext());
            startActivity(intent);
        });

        binding.kitchenAddButton.setOnClickListener(v -> {
            Intent intent = KitchenAdd.kitchenAddActivityIntentFactory(getApplicationContext());
            startActivity(intent);
        });


    }


    private void recyclerViewSetup(){
        setUpLists();
        getFoodList();

        KitchenAdapter adapter = new KitchenAdapter(KitchenActivity.this, kitchenList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(null));
    }


    private void getFoodList() {
        LiveData<List<String>> userObserver = kitchenRepository.getFoodList();
        userObserver.observe(this, list -> {
            this.foodList = (ArrayList<String>) list;
            if (list != null) {
                invalidateOptionsMenu();
            }
        });
    }

    private void setUpLists() {
        try {
            for (Kitchen kitchens : kitchenRepository.getAllLogs()) {
                if (kitchens.getUserId() == getLoggedInUserId()) { //ensures stored within same user
                    kitchenList.add(kitchens);
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Could not add to kitchen list", Toast.LENGTH_SHORT).show();
        }

        try {
            for (Kitchen food : kitchenRepository.getAllLogs()) {
                foodList.add(food.getName());
            }
        } catch (Exception e) {
            Toast.makeText(this, "Could not add to kitchen list", Toast.LENGTH_SHORT).show();
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