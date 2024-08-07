package com.beaconcode.kitchinventory.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beaconcode.kitchinventory.R;
import com.beaconcode.kitchinventory.data.database.KitchenRepository;
import com.beaconcode.kitchinventory.data.database.entities.Kitchen;
import com.beaconcode.kitchinventory.databinding.ActivityCookBinding;
import com.beaconcode.kitchinventory.ui.adapters.CookAdapter;
import com.beaconcode.kitchinventory.ui.adapters.ShoppingListAdapter;
import com.beaconcode.kitchinventory.ui.view.CookInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity class for displaying a list of food items and handling item click events.
 * This activity implements the CookInterface to handle item click events in the RecyclerView.
 * TODO: Future implementation of this activity should display a list of food items that is fetched from room database.
 */
public class CookActivity extends AppCompatActivity implements CookInterface {

    private String foodName;
    private ArrayList<String> foodList = new ArrayList<>();
    private ActivityCookBinding binding;
    private KitchenRepository kitchenRepository;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.rvCook;

        kitchenRepository = KitchenRepository.getRepository(getApplication());

        recyclerViewSetup();
    }

    private void recyclerViewSetup(){
        setUpFoodList();
        getFoodList();
        ShoppingListAdapter adapter = new ShoppingListAdapter(CookActivity.this, foodList, CookActivity.this);
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

    /**
     * Sets up the list of food items to be displayed in the RecyclerView.
     * Adds predefined food items to the foodList.
     * This method should be replaced with fetching data from a room database when possible.
     */
    private void setUpFoodList() {
        try {
            for (Kitchen food : kitchenRepository.getAllLogs()) {
                foodList.add(food.getName());
            }
        } catch (Exception e) {
            Toast.makeText(this, "Unknown error", Toast.LENGTH_SHORT).show();
        }
    }


/**
 * Initializes the contents of the Activity's standard options menu.
 *
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
 * Factory method to create an Intent for starting CookActivity.
 *
 * @param context The context from which the activity is started.
 * @return An Intent to start CookActivity.
 */
static Intent cookActivityIntentFactory(Context context) {
    return new Intent(context, CookActivity.class);
}

/**
 * Called when an item in the RecyclerView is clicked.
 * Starts the RecipesActivity with the selected food item.
 *
 * @param foodName The name of the food item that was clicked.
 */
@Override
public void onItemClick(String foodName) {
    Intent intent = RecipesActivity.recipesActivityIntentFactory(getApplicationContext(), foodName);
    startActivity(intent);
}
}