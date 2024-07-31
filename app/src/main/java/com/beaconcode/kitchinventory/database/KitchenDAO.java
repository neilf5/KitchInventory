package com.beaconcode.kitchinventory.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.beaconcode.kitchinventory.database.entities.Kitchen;

/**
 * Kitchen DAO
 * This is the DAO that manages requests for kitchen_table
 */
@Dao
public interface KitchenDAO {

    @Insert
    void insert(Kitchen... kitchens);

    @Update
    void update(Kitchen... kitchens);

    @Delete
    void delete(Kitchen kitchens);

    //@Query()
}
