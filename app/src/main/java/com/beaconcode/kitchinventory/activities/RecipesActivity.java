package com.beaconcode.kitchinventory.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beaconcode.kitchinventory.R;
import com.beaconcode.kitchinventory.databinding.ActivityRecipesBinding;
import com.beaconcode.kitchinventory.ui.adapters.RecipesAdapter;
import com.beaconcode.kitchinventory.ui.view.RecipesInterface;
import com.beaconcode.kitchinventory.ui.viewmodels.RecipesViewModel;

/**
 * Activity to display a list of recipes based on a selected ingredient.
 * This activity fetches recipes from an API and displays them in a RecyclerView.
 */
public class RecipesActivity extends BaseActivity implements RecipesInterface {

    private static final String COOK_ACTIVITY_FOOD_NAME = "com.beaconcode.kitchinventory.COOK_ACTIVITY_FOOD_NAME";

    private RecipesViewModel recipesViewModel;
    private ActivityRecipesBinding binding;
    private RecipesAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Retrieve the food name passed from the previous activity
        String foodName = getIntent().getStringExtra(COOK_ACTIVITY_FOOD_NAME);
        binding.tvIngredientName.setText(foodName);

        recipesViewModel = new RecipesViewModel();

        // Set up the RecyclerView with the adapter and layout manager
        RecyclerView recyclerView = binding.rvRecipes;
        adapter = new RecipesAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Observe the LiveData from the ViewModel and update the adapter when data changes
        recipesViewModel.getMeals().observe(this, meals -> {
            if (meals != null) {
                adapter.submitList(meals);
            } else {
                Toast.makeText(this, "Failed to get recipes", Toast.LENGTH_SHORT).show();
            }
        });
        // Fetch the meals based on the ingredient name
        recipesViewModel.getMealsByIngredient(foodName);
    }

    /**
     * Factory method to create an Intent for RecipesActivity.
     * @param context The context from which the activity is started.
     * @param foodName The name of the ingredient to fetch recipes for.
     * @return An Intent to start RecipesActivity.
     */
    static public Intent recipesActivityIntentFactory(Context context, String foodName) {
        Intent intent = new Intent(context, RecipesActivity.class);
        intent.putExtra(COOK_ACTIVITY_FOOD_NAME, foodName);
        return intent;
    }

    /**
     * Initialize the contents of the Activity's standard options menu.
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed; if you return false it will not be shown.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public void onRecipeClick(int position) {
        // Get the meal ID of the clicked recipe
        String mealId = adapter.getCurrentList().get(position).getIdMeal();
        // Start the RecipeDetails activity with the meal ID
        Intent intent = RecipeDetailsActivity.recipeDetailsIntentFactory(this, mealId);
        startActivity(intent);
    }
}