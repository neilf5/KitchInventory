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
}
