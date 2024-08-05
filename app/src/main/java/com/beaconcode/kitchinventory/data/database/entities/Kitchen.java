package com.beaconcode.kitchinventory.data.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.beaconcode.kitchinventory.data.database.KitchenDatabase;

import java.util.Objects;

/**
 * Kitchen Class
 * Organizes the data of ingredients that user currently has in kitchen
 */

@Entity(tableName = KitchenDatabase.KITCHEN_TABLE)
public class Kitchen {

    @PrimaryKey(autoGenerate = true)
    private int inventoryId;
    private int foodId;
    private int userId;

    private String name;
    private int quantity;
    private double price;


    //constructor
    public Kitchen(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
