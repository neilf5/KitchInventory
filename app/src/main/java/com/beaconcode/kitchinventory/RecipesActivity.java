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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesActivity extends AppCompatActivity {

    private static final String COOK_ACTIVITY_FOOD_NAME = "com.beaconcode.kitchinventory.COOK_ACTIVITY_FOOD_NAME";

    private ActivityRecipesBinding binding;
    private List<Meal> mealList = new ArrayList<>();
    private RecipeServiceHelper recipeServiceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String foodName = getIntent().getStringExtra(COOK_ACTIVITY_FOOD_NAME);
        binding.tvIngredientName.setText(foodName);


        recipeServiceHelper = new RecipeServiceHelper();


        RecyclerView recyclerView = binding.rvRecipes;
        RecipesAdapter adapter = new RecipesAdapter(this, mealList);
        //recyclerView.setAdapter(new RecipesAdapter(this, mealList));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Call<Meals> call = recipeServiceHelper.getRecipesByIngredient(foodName);
        call.enqueue(new Callback<Meals>() {
            @Override
            public void onResponse(@NonNull Call<Meals> call, @NonNull Response<Meals> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mealList.addAll(response.body().getMeals());
                    recyclerView.getAdapter().notifyDataSetChanged();
                } else {
                    Toast.makeText(RecipesActivity.this, "Error loading recipes", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Meals> call, Throwable t) {
                Toast.makeText(RecipesActivity.this, "FAILURE", Toast.LENGTH_SHORT).show();
            }
        });


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