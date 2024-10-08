package com.beaconcode.kitchinventory.data.model;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a meal item that is fetched from the mealdb API.
 * Holds the name of the meal, the URL of the meal image, and the meal ID.
 * It also holds the instructions for preparing the meal, and the list of ingredients and their measures.
 */
@SuppressWarnings("unused")
public class Meal {
    private String strMeal;
    private String strMealThumb;
    private String idMeal;
    private String strInstructions;
    private String strIngredient1;
    private String strIngredient2;
    private String strIngredient3;
    private String strIngredient4;
    private String strIngredient5;
    private String strIngredient6;
    private String strIngredient7;
    private String strIngredient8;
    private String strIngredient9;
    private String strIngredient10;
    private String strIngredient11;
    private String strIngredient12;
    private String strIngredient13;
    private String strIngredient14;
    private String strIngredient15;
    private String strIngredient16;
    private String strIngredient17;
    private String strIngredient18;
    private String strIngredient19;
    private String strIngredient20;
    private String strMeasure1;
    private String strMeasure2;
    private String strMeasure3;
    private String strMeasure4;
    private String strMeasure5;
    private String strMeasure6;
    private String strMeasure7;
    private String strMeasure8;
    private String strMeasure9;
    private String strMeasure10;
    private String strMeasure11;
    private String strMeasure12;
    private String strMeasure13;
    private String strMeasure14;
    private String strMeasure15;
    private String strMeasure16;
    private String strMeasure17;
    private String strMeasure18;
    private String strMeasure19;
    private String strMeasure20;

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

    public String getStrInstructions() {
        return strInstructions;
    }

    /**
     * Get the list of non-empty ingredients.
     * This method uses reflection to get all the strIngredient fields in the class.
     * It then checks if the field is non-empty and adds it to the list of ingredients.
     * @return A list of non-empty ingredients.
     */
    public List<String> getNonEmptyIngredients() {
        List<String> ingredients = new ArrayList<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().startsWith("strIngredient")) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                try {
                    String ingredient = (String) field.get(this);
                    if (ingredient != null && !ingredient.isEmpty()) {
                        ingredients.add(ingredient);
                        Log.d("Meal", "Ingredient: " + ingredient);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }
        return ingredients;
    }

    /**
     * Get the list of non-empty measures.
     * This method uses reflection to get all the strMeasure fields in the class.
     * It then checks if the field is non-empty and adds it to the list of measures.
     * @return A list of non-empty measures.
     */
    public List<String> getNonEmptyMeasures() {
        List<String> measures = new ArrayList<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().startsWith("strMeasure")) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                try {
                    String measure = (String) field.get(this);
                    if (measure != null && !measure.trim().isEmpty()) {
                        measures.add(measure);
                        Log.d("Meal", "Measure: " + measure);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    Log.e("Meal", "Error getting measure: " + e.getMessage());
                }
            }
        }
        return measures;
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
