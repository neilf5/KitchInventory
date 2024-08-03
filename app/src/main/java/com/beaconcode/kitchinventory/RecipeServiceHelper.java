package com.beaconcode.kitchinventory;

import java.util.List;

import retrofit2.Call;

public class RecipeServiceHelper {
    private RecipeApiService recipeApiService;

    public RecipeServiceHelper() {
        recipeApiService = RetrofitClient.getInstance().create(RecipeApiService.class);
    }

    public Call<Meals> getRecipesByIngredient(String ingredient) {
        return recipeApiService.getRecipesByIngredient(ingredient);
    }

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
