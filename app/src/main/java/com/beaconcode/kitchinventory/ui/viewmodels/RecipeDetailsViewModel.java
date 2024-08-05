package com.beaconcode.kitchinventory.ui.viewmodels;

import androidx.lifecycle.ViewModel;

import com.beaconcode.kitchinventory.utils.RecipeServiceHelper;

public class RecipeDetailsViewModel extends ViewModel {
    private final RecipeServiceHelper recipeServiceHelper;

    public RecipeDetailsViewModel() {
        recipeServiceHelper = new RecipeServiceHelper();
    }


}
