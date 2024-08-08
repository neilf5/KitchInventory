package com.beaconcode.kitchinventory.data.api;

import com.beaconcode.kitchinventory.data.model.Meals;

import retrofit2.Call;

/**
 * Helper class for interacting with the Recipe API service.
 * This class provides methods to fetch recipes based on ingredients using the RecipeApiService.
 */
public class RecipeServiceHelper {
    private RecipeApiService recipeApiService;

    /**
     * Constructor for RecipeServiceHelper.
     * Initializes the RecipeApiService instance using RetrofitClient.
     */
    public RecipeServiceHelper() {
        recipeApiService = RetrofitClient.getInstance().create(RecipeApiService.class);
    }

    /**
     * Fetches a recipe by its ID.
     * @param id The ID of the recipe to fetch.
     * @return A Call object that can be used to request the recipe.
     */
    public Call<Meals> getRecipeById(String id) {
        return recipeApiService.getRecipeById(id);
    }

    /**
     * Fetches a list of meals that contain the specified ingredient.
     * @param ingredient The ingredient to filter recipes by.
     * @return A Call object that can be used to request the list of meals.
     */
    public Call<Meals> getRecipesByIngredient(String ingredient) {
        return recipeApiService.getRecipesByIngredient(ingredient);
    }

//    This was an alternative implementation of the getRecipesByIngredient method that defined a custom callback interface.
//    This is now being handled directly in the ViewModel using LiveData. Using for reference only.
//
//    public void getRecipesByIngredient(String ingredient, Callback<List<Recipe>> callback) {
//        Call<List<Recipe>> call = recipeApiService.getRecipesByIngredient(ingredient);
//        call.enqueue(new Callback<List<Recipe>>() {
//            @Override
//            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    callback.onResponse(call, response);
//                    // Do something with the recipes
//                } else {
//                    // Handle the error
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Recipe>> call, Throwable t) {
//                callback.onFailure(call, t);
//            }
//        });
//    }
}
