package com.beaconcode.kitchinventory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beaconcode.kitchinventory.databinding.ActivityRecipesBinding;
import com.beaconcode.kitchinventory.views.RecipesAdapter;
import com.beaconcode.kitchinventory.views.RecipesViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesActivity extends AppCompatActivity {

    private static final String COOK_ACTIVITY_FOOD_NAME = "com.beaconcode.kitchinventory.COOK_ACTIVITY_FOOD_NAME";

    private RecipesViewModel recipesViewModel;
    private ActivityRecipesBinding binding;
    private RecipesAdapter adapter;
    private List<Meal> mealList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String foodName = getIntent().getStringExtra(COOK_ACTIVITY_FOOD_NAME);
        binding.tvIngredientName.setText(foodName);


        recipesViewModel = new RecipesViewModel();


        RecyclerView recyclerView = binding.rvRecipes;
        adapter = new RecipesAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recipesViewModel.getMeals().observe(this, meals -> {
            if (meals != null) {
                adapter.submitList(meals);
            } else {
                Toast.makeText(this, "Failed to get recipes", Toast.LENGTH_SHORT).show();
            }
        });
        recipesViewModel.getMealsByIngredient(foodName);
    }

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