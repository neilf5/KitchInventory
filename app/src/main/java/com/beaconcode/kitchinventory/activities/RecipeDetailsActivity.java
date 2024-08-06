package com.beaconcode.kitchinventory.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.beaconcode.kitchinventory.data.model.Meal;
import com.beaconcode.kitchinventory.data.model.Meals;
import com.beaconcode.kitchinventory.databinding.ActivityRecipeDetailsBinding;
import com.beaconcode.kitchinventory.ui.adapters.IngredientsAdapter;
import com.beaconcode.kitchinventory.ui.viewmodels.RecipeDetailsViewModel;
import com.beaconcode.kitchinventory.utils.RecipeServiceHelper;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetailsActivity extends AppCompatActivity {

    private static final String RECIPES_ACTIVITY_MEAL_ID = "com.beaconcode.kitchinventory.RECIPES_ACTIVITY_MEAL_ID";

    private ActivityRecipeDetailsBinding binding;
    private IngredientsAdapter ingredientsAdapter;
    private RecipeDetailsViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the ViewModel
        viewModel = new RecipeDetailsViewModel();

        // Retrieve the meal ID passed from the previous activity
        String mealId = getIntent().getStringExtra(RECIPES_ACTIVITY_MEAL_ID);


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

//        RecipeServiceHelper helper = new RecipeServiceHelper();
//        Call<Meals> call = helper.getRecipeById(mealId);
//        call.enqueue(new Callback<Meals>() {
//            @Override
//            public void onResponse(Call<Meals> call, Response<Meals> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    List<Meal> meals = response.body().getMeals();
//                    if (!meals.isEmpty()) {
//                        Meal meal = meals.get(0); // Assuming you're interested in the first meal
//                        List<String> ingredients = meal.getNonEmptyIngredients();
//                        List<String> measures = meal.getNonEmptyMeasures();
//
//                        StringBuilder measureText = new StringBuilder();
//                        StringBuilder ingredientsText = new StringBuilder();
//                        for (int i = 0; i < ingredients.size(); i++) {
//                            ingredientsText
//                                    .append(measures.size() > i ? measures.get(i) : "") // Safeguard against mismatch in sizes
//                                    .append(" ")
//                                    .append(ingredients.get(i))
//                                    .append(System.lineSeparator());
////                            measureText.append("- ")
////                                    .append(" ")
////                                    .append(measures.get(i))
////                                    .append(System.lineSeparator());
//                        }
//
//                        binding.tvIngredientName.setText(ingredientsText.toString());
////                        binding.tvMeasurement.setText(measureText.toString());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Meals> call, Throwable t) {
//                // Handle the error scenario
//            }
//        });
//
//    }


    static Intent recipeDetailsIntentFactory(Context context, String mealId) {
        Intent intent = new Intent(context, RecipeDetailsActivity.class);
        intent.putExtra(RECIPES_ACTIVITY_MEAL_ID, mealId);
        return intent;
    }

}