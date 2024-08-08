package com.beaconcode.kitchinventory.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.beaconcode.kitchinventory.databinding.ActivityRecipeDetailsBinding;
import com.beaconcode.kitchinventory.ui.adapters.IngredientsAdapter;
import com.beaconcode.kitchinventory.ui.viewmodels.RecipeDetailsViewModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 *  Activity to display the details of a recipe.
 *  This activity is responsible for displaying the details of a selected recipe, including
 *  the recipe name, image, instructions, and a list of ingredients with their measurements.
 *  It uses a ViewModel to fetch and observe the recipe data.
 */
public class RecipeDetailsActivity extends BaseActivity {

    private static final String RECIPES_ACTIVITY_MEAL_ID = "com.beaconcode.kitchinventory.RECIPES_ACTIVITY_MEAL_ID";

    private ActivityRecipeDetailsBinding binding;
    private IngredientsAdapter ingredientsAdapter;
    private RecipeDetailsViewModel viewModel;


    /**
     * This method initializes the activity, sets up the ViewModel, retrieves the meal ID from the intent,
     * and sets up the RecyclerView for displaying the ingredients.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the ViewModel
        viewModel = new RecipeDetailsViewModel();

        // Retrieve the meal ID passed from the previous activity
        String mealId = getIntent().getStringExtra(RECIPES_ACTIVITY_MEAL_ID);

        // Set up the RecyclerView
        binding.rvIngredients.setLayoutManager(new LinearLayoutManager(this));
        ingredientsAdapter = new IngredientsAdapter(new ArrayList<>(), new ArrayList<>());
        binding.rvIngredients.setAdapter(ingredientsAdapter);

        // Observe the LiveData from the ViewModel and update the adapter when data changes
        viewModel.getMeal().observe(this, meal -> {
            if (meal != null) {
                List<String> ingredients = meal.getNonEmptyIngredients();
                List<String> measures = meal.getNonEmptyMeasures();
                ingredientsAdapter.updateData(ingredients, measures);

                binding.tvRecipeName.setText(meal.getStrMeal());
                binding.tvRecipeInstructions.setText(meal.getStrInstructions());
                Glide.with(this)
                        .load(meal.getStrMealThumb())
                        .into(binding.ivRecipeImage);
            } else {
                Toast.makeText(this, "Ingredients list is empty!", Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.getRecipeById(mealId);

    }


    /**
     * This method creates an Intent to start the RecipeDetailsActivity.
     * @param context The context from which the intent is created.
     * @param mealId The ID of the meal to display.
     * @return The Intent to start the RecipeDetailsActivity.
     */
    public static Intent recipeDetailsIntentFactory(Context context, String mealId) {
        Intent intent = new Intent(context, RecipeDetailsActivity.class);
        intent.putExtra(RECIPES_ACTIVITY_MEAL_ID, mealId);
        return intent;
    }

}