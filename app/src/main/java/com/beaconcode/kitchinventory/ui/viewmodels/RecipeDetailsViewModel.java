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

public class RecipeDetailsViewModel extends ViewModel {
    private final MutableLiveData<Meal> meal = new MutableLiveData<>();
    private final RecipeServiceHelper recipeServiceHelper;

    public RecipeDetailsViewModel() {
        recipeServiceHelper = new RecipeServiceHelper();
    }

    public LiveData<Meal> getMeal() {
        return meal;
    }

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
