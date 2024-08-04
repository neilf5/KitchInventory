package com.beaconcode.kitchinventory.views;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.beaconcode.kitchinventory.Meal;
import com.beaconcode.kitchinventory.Meals;
import com.beaconcode.kitchinventory.RecipeServiceHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesViewModel extends ViewModel {
    private final MutableLiveData<List<Meal>> meals = new MutableLiveData<>();
    private final RecipeServiceHelper recipeServiceHelper;

    public RecipesViewModel() {
        recipeServiceHelper = new RecipeServiceHelper();
    }

    public LiveData<List<Meal>> getMeals() {
        return meals;
    }

    public void getMealsByIngredient(String ingredient) {
        Call<Meals> call = recipeServiceHelper.getRecipesByIngredient(ingredient);
        call.enqueue(new Callback<Meals>() {
            @Override
            public void onResponse(@NonNull Call<Meals> call, @NonNull Response<Meals> response) {
                if (response.isSuccessful() && response.body() != null) {
                    meals.setValue(response.body().getMeals());
                } else {
                    meals.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Meals> call, @NonNull Throwable t) {
                meals.setValue(null);
            }
        });
    }
}