package com.beaconcode.kitchinventory.data.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.beaconcode.kitchinventory.data.database.KitchenDatabase;

import java.util.Objects;

/**
 * POJO representing a shopping list
 */

@Entity(tableName = KitchenDatabase.SHOPPING_LIST_TABLE)
public class ShoppingList {

    @PrimaryKey(autoGenerate = true)
    private int inventoryId;
    private int foodId;
    private int userId;

    private String name;
    private int quantity;
    private double price;

    public ShoppingList(String name, int quantity, int userId) {
        this.name = name;
        this.quantity = quantity;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingList shoppingList = (ShoppingList) o;
        return inventoryId == shoppingList.inventoryId && foodId == shoppingList.foodId && userId == shoppingList.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(inventoryId, foodId, userId);
    }

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


