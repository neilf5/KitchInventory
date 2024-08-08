package com.beaconcode.kitchinventory.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.beaconcode.kitchinventory.data.database.entities.ShoppingList;


import java.util.List;

/**
 * ShoppingList DAO
 * This handles any transactions made to shoppingList_table
 */
@Dao
public interface ShoppingListDAO {
    /**
     * insert item into database
     */
    @Insert
    void insert(ShoppingList... shoppingLists);

    /**
     * update item in database
     */
    @Update
    void update(ShoppingList... shoppingLists);

    /**
     * delete item from database
     */
    @Delete
    void delete(ShoppingList shoppingList);

    /**
     * display all entries in database
     */
    @Query("SELECT * from " + KitchenDatabase.SHOPPING_LIST_TABLE)
    List<ShoppingList> getAllShoppingList();

    @Query("SELECT name from " + KitchenDatabase.SHOPPING_LIST_TABLE + " WHERE quantity > 0")
    LiveData<List<String>> getFoodList();

    @Query("DELETE FROM " + KitchenDatabase.SHOPPING_LIST_TABLE + " WHERE name == :foodName")
    void deleteByFoodName(String foodName);

    @Query("DELETE from " + KitchenDatabase.SHOPPING_LIST_TABLE + " WHERE userId == :userId")
    void clearShoppingListByUserId(int userId);

    @Query("SELECT SUM(quantity) FROM " + KitchenDatabase.SHOPPING_LIST_TABLE + " WHERE userId = :userId")
    LiveData<Integer> getTotalQuantityByUserId(int userId);
}

