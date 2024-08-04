package com.beaconcode.kitchinventory.views;

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

/**
 * ViewModel class for managing and providing data related to recipes.
 * This ViewModel interacts with the RecipeServiceHelper to fetch data from a remote source.
 */
public class RecipesViewModel extends ViewModel {
    private final MutableLiveData<List<Meal>> meals = new MutableLiveData<>();
    private final RecipeServiceHelper recipeServiceHelper;

    /**
     * Constructor for RecipesViewModel.
     * Initializes the RecipeServiceHelper.
     */
    public RecipesViewModel() {
        recipeServiceHelper = new RecipeServiceHelper();
    }

    /**
     * Returns a LiveData object containing a list of meals.
     * This LiveData object can be observed by the UI to update the list of meals.
     * @return LiveData object containing a list of meals.
     */
    public LiveData<List<Meal>> getMeals() {
        return meals;
    }

    /**
     * Fetches meals based on the provided ingredient.
     * Makes a network call to retrieve recipes containing the specified ingredient.
     * @param ingredient The ingredient to search recipes for.
     */
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