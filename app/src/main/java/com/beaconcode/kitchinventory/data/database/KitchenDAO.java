package com.beaconcode.kitchinventory.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.beaconcode.kitchinventory.data.database.entities.Kitchen;

import java.util.List;

/**
 * Kitchen DAO
 * This is the DAO that manages requests for kitchen_table
 */
@Dao
public interface KitchenDAO {

    /**
     * inserts Kitchen objects into the database
     */
    @Insert
    void insert(Kitchen... kitchens);

    /**
     * updates specific Kitchen objects in the database
     */
    @Update
    void update(Kitchen... kitchens);

    /**
     * deletes Kitchen objects from the database
     */
    @Delete
    void delete(Kitchen kitchens);

    /**
     * Selects all rows from the database
     */
    @Query("SELECT * from " + KitchenDatabase.KITCHEN_TABLE)
    List<Kitchen> getAllRecords();

    @Query("SELECT name from " + KitchenDatabase.KITCHEN_TABLE + " WHERE quantity > 0")
    LiveData<List<String>> getFoodList();
}
