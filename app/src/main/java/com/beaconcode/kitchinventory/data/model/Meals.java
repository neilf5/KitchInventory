package com.beaconcode.kitchinventory.data.model;

import java.util.List;

/**
 * Represents a list of meals fetched from the mealdb API.
 * Holds a list of Meal objects.
 */
@SuppressWarnings("unused")
public class Meals {
    private List<Meal> meals;

    // Getter and Setter
    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}