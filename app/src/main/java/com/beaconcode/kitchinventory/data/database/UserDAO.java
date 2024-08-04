package com.beaconcode.kitchinventory.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.beaconcode.kitchinventory.data.database.entities.Kitchen;
import com.beaconcode.kitchinventory.data.database.entities.User;

import java.util.List;

/**
 * User DAO
 * This is the DAO that manages requests for user_table
 */
@Dao
public interface UserDAO {

    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User users);

    @Query("SELECT * from " + KitchenDatabase.USER_TABLE)
    List<User> getAllRecords();
}
