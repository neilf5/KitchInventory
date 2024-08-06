package com.beaconcode.kitchinventory.data.api;

import com.beaconcode.kitchinventory.data.model.Meals;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interface for the Recipe API service.
 * This interface defines the methods for interacting with the mealdb API to fetch recipes based on ingredients.
 */
public interface RecipeApiService {

    /**
     * Fetches a list of meals by main ingredient.
     * Makes a GET request to the "filter.php" endpoint with the ingredient as a query parameter.
     * @param ingredient The ingredient to filter recipes by.
     * @return A Call object that can be used to request the list of meals.
     */
    @GET("filter.php")
    Call<Meals> getRecipesByIngredient(
            @Query("i") String ingredient);


    /**
     * Fetches a recipe by its ID.
     * Makes a GET request to the "lookup.php" endpoint with the recipe ID as a query parameter.
     * @param id
     * @return
     */
    @GET("lookup.php")
    Call<Meals> getRecipeById(
            @Query("i") String id);
}
