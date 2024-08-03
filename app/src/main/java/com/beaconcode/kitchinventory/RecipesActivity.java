package com.beaconcode.kitchinventory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beaconcode.kitchinventory.databinding.ActivityRecipesBinding;
import com.beaconcode.kitchinventory.views.RecipesAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecipesActivity extends AppCompatActivity {

    private static final String COOK_ACTIVITY_FOOD_NAME = "com.beaconcode.kitchinventory.COOK_ACTIVITY_FOOD_NAME";

    private ActivityRecipesBinding binding;
    private List<Recipe> recipeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String foodName = getIntent().getStringExtra(COOK_ACTIVITY_FOOD_NAME);
        binding.tvIngredientName.setText(foodName);

        RecyclerView recyclerView = binding.rvRecipes;
        recyclerView.setAdapter(new RecipesAdapter(this, recipeList));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        };

    static Intent recipesActivityIntentFactory(Context context, String foodName) {
        Intent intent = new Intent(context, RecipesActivity.class);
        intent.putExtra(COOK_ACTIVITY_FOOD_NAME, foodName);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

}