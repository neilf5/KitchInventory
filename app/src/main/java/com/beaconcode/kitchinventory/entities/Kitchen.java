package com.beaconcode.kitchinventory.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Objects;

/**
 * Kitchen Class
 * Organizes the data of ingredients that user currently has in kitchen
 */

@Entity(tableName = "Kitchen")
public class Kitchen {

    @PrimaryKey(autoGenerate = true)

    private int inventoryID;
    private int foodID;
    private int userID;

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
    public int getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(int inventoryID) {
        this.inventoryID = inventoryID;
    }

    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    //equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kitchen kitchen = (Kitchen) o;
        return inventoryID == kitchen.inventoryID && foodID == kitchen.foodID && userID == kitchen.userID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(inventoryID, foodID, userID);
    }
}
