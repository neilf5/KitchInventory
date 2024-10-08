package com.beaconcode.kitchinventory.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beaconcode.kitchinventory.R;
import com.beaconcode.kitchinventory.databinding.ActivityCookBinding;
import com.beaconcode.kitchinventory.ui.adapters.CookAdapter;
import com.beaconcode.kitchinventory.ui.view.CookInterface;
import com.beaconcode.kitchinventory.ui.viewmodels.CookViewModel;

/**
 * Activity class for displaying a list of food items and handling item click events.
 * This activity implements the CookInterface to handle item click events in the RecyclerView.
 * The activity uses a ViewModel to fetch and observe the list of food items.
 * The RecyclerView displays the food items in a list.
 */
public class CookActivity extends BaseActivity implements CookInterface {

    private ActivityCookBinding binding;
    private CookAdapter adapter;
    private CookViewModel viewModel;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new CookViewModel(getApplication());
        adapter = new CookAdapter(this, this);
        recyclerView = binding.rvCook;

        viewModel.getFoodList().observe(this, adapter::submitList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(null));


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
public static Intent cookActivityIntentFactory(Context context) {
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