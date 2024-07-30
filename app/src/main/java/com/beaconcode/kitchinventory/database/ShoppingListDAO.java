package com.beaconcode.kitchinventory.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface ShoppingListDAO {


    @Insert
    void insert(ShoppingList... shoppingLists);

    @Update
    void update(ShoppingList... shoppingLists);

    @Delete
    void delete(ShoppingList shoppingLists);

    //@Query()
}
