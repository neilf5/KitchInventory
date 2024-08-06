package com.beaconcode.kitchinventory.ui.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.beaconcode.kitchinventory.data.model.Meal;
import com.beaconcode.kitchinventory.data.model.Meals;
import com.beaconcode.kitchinventory.utils.RecipeServiceHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ViewModel for RecipeDetailsActivity.
 * This ViewModel is responsible for managing and providing the data for the RecipeDetailsActivity.
 * It fetches the recipe details from the RecipeServiceHelper and exposes the data as LiveData.
 */
public class RecipeDetailsViewModel extends ViewModel {
    private final MutableLiveData<Meal> meal = new MutableLiveData<>();
    private final RecipeServiceHelper recipeServiceHelper;

    /**
     * Constructor for RecipeDetailsViewModel.
     * Initializes the RecipeServiceHelper.
     */
    public RecipeDetailsViewModel() {
        recipeServiceHelper = new RecipeServiceHelper();
    }

    /**
     * Returns the LiveData object containing the meal data.
     * @return LiveData object containing the meal data.
     */
    public LiveData<Meal> getMeal() {
        return meal;
    }

    /**
     * Fetches the recipe details by the given recipe ID.
     * Makes a network call to retrieve the recipe details and updates the LiveData object.
     * @param id The ID of the recipe to fetch.
     */
    public void getRecipeById(String id) {
        Call<Meals> call = recipeServiceHelper.getRecipeById(id);
        call.enqueue(new Callback<Meals>() {

            @Override
            public void onResponse(Call<Meals> call, Response<Meals> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Meal> meals = response.body().getMeals();
                    if (!meals.isEmpty()) {
                        meal.setValue(meals.get(0));
                    }
                } else {
                    meal.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Meals> call, @NonNull Throwable t) {
                meal.setValue(null);
            }
        });
    }
}
