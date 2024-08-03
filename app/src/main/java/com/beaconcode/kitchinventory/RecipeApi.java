package com.beaconcode.kitchinventory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeApi {

    @GET("filter.php")
    Call<List<Recipe>> recipesByMainIngredient(
            @Query("i") String ingredient);
}
