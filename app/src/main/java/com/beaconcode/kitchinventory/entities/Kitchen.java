package com.beaconcode.kitchinventory.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Objects;

/**
 * Kitchen Class
 * Organizes the data of ingredients that user currently has in kitchen
 */

@Entity(tableName = "kitchen_table")
public class Kitchen {

    @PrimaryKey(autoGenerate = true)

    private int inventoryId;
    private int foodId;
    private int userId;

    private String name;
    private int quantity;
    private double price;


    //constructor
    public Kitchen(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }


    //getters and setters
    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    //equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kitchen kitchen = (Kitchen) o;
        return inventoryId == kitchen.inventoryId && foodId == kitchen.foodId && userId == kitchen.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(inventoryId, foodId, userId);
    }
}
