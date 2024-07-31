package com.beaconcode.kitchinventory.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ShoppingListDAO {


    @Insert
    void insert(ShoppingList... shoppingLists);

    @Update
    void update(ShoppingList... shoppingLists);

    @Delete
    void delete(ShoppingList shoppingLists);

    //@Query("SELECT * FROM " + ShoppingListDatabase.shoppingList_Table)
    //List<ShoppingList> getShoppingLists();

   // @Query("SELECT * FROM " + ShoppingListDatabase.shoppingList_Table + "WHERE")
    //Kitchen getShoppingListByTODO(int TODO);
}
