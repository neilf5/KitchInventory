package com.beaconcode.kitchinventory.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.beaconcode.kitchinventory.data.database.entities.ShoppingList;
import com.beaconcode.kitchinventory.data.database.entities.User;

import java.util.List;

/**
 * ShoppingList DAO
 * This is the DAO that manages requests for shoppingList_table
 */
@Dao
public interface ShoppingListDAO {


    @Insert
    void insert(ShoppingList... shoppingLists);

    @Update
    void update(ShoppingList... shoppingLists);

    @Delete
    void delete(ShoppingList shoppingLists);

    @Query("SELECT * from " + KitchenDatabase.SHOPPING_LIST_TABLE)
    List<ShoppingList> getAllRecords();

    @Query("DELETE from " + KitchenDatabase.SHOPPING_LIST_TABLE + " WHERE userId = :userId")
    void clearShoppingListByUserId(int userId);

    @Query("SELECT SUM(quantity) FROM " + KitchenDatabase.SHOPPING_LIST_TABLE + " WHERE userId = :userId")
    LiveData<Integer> getTotalQuantityByUserId(int userId);
}

