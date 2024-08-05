package com.beaconcode.kitchinventory.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.beaconcode.kitchinventory.data.model.Meal;
import com.beaconcode.kitchinventory.data.model.Meals;
import com.beaconcode.kitchinventory.databinding.ActivityRecipeDetailsBinding;
import com.beaconcode.kitchinventory.utils.RecipeServiceHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetailsActivity extends AppCompatActivity {

    private static final String RECIPES_ACTIVITY_MEAL_ID = "com.beaconcode.kitchinventory.RECIPES_ACTIVITY_MEAL_ID";

    private ActivityRecipeDetailsBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Retrieve the meal ID passed from the previous activity
        String mealId = getIntent().getStringExtra(RECIPES_ACTIVITY_MEAL_ID);

        RecipeServiceHelper helper = new RecipeServiceHelper();
        Call<Meals> call = helper.getRecipeById(mealId);
        call.enqueue(new Callback<Meals>() {
            @Override
            public void onResponse(Call<Meals> call, Response<Meals> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Meal> meals = response.body().getMeals();
                    if (!meals.isEmpty()) {
                        Meal meal = meals.get(0); // Assuming you're interested in the first meal
                        List<String> ingredients = meal.getNonEmptyIngredients();
                        List<String> measures = meal.getNonEmptyMeasures();

                        StringBuilder measureText = new StringBuilder();
                        StringBuilder ingredientsText = new StringBuilder();
                        for (int i = 0; i < ingredients.size(); i++) {
                            ingredientsText
                                    .append(measures.size() > i ? measures.get(i) : "") // Safeguard against mismatch in sizes
                                    .append(" ")
                                    .append(ingredients.get(i))
                                    .append(System.lineSeparator());
//                            measureText.append("- ")
//                                    .append(" ")
//                                    .append(measures.get(i))
//                                    .append(System.lineSeparator());
                        }

                        binding.tvIngredientName.setText(ingredientsText.toString());
//                        binding.tvMeasurement.setText(measureText.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<Meals> call, Throwable t) {
                // Handle the error scenario
            }
        });

    }


    static Intent recipeDetailsIntentFactory(Context context, String mealId) {
        Intent intent = new Intent(context, RecipeDetailsActivity.class);
        intent.putExtra(RECIPES_ACTIVITY_MEAL_ID, mealId);
        return intent;
    }

}