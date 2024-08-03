package com.beaconcode.kitchinventory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeApiService {

    @GET("filter.php")
    Call<Meals> getRecipesByIngredient(
            @Query("i") String ingredient);
}
