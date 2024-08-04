package com.beaconcode.kitchinventory.data.model;

import java.util.Objects;

/**
 * Represents a meal item that is fetched from the mealdb API.
 * Holds the name of the meal, the URL of the meal image, and the meal ID.
 */
public class Meal {
    private String strMeal;
    private String strMealThumb;
    private String idMeal;

    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }

    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(String idMeal) {
        this.idMeal = idMeal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return Objects.equals(strMeal, meal.strMeal) &&
                Objects.equals(strMealThumb, meal.strMealThumb) &&
                Objects.equals(idMeal, meal.idMeal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(strMeal, strMealThumb, idMeal);
    }
}
