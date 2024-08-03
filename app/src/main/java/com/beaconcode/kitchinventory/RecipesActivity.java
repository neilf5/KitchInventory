package com.beaconcode.kitchinventory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.beaconcode.kitchinventory.databinding.ActivityRecipesBinding;

public class RecipesActivity extends AppCompatActivity {

    private static final String COOK_ACTIVITY_FOOD_NAME = "com.beaconcode.kitchinventory.COOK_ACTIVITY_FOOD_NAME";

    private ActivityRecipesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String foodName = getIntent().getStringExtra(COOK_ACTIVITY_FOOD_NAME);
        binding.tvIngredientName.setText(foodName);
        };

    static Intent recipesActivityIntentFactory(Context context, String foodName) {
        Intent intent = new Intent(context, RecipesActivity.class);
        intent.putExtra(COOK_ACTIVITY_FOOD_NAME, foodName);
        return intent;
    }
}