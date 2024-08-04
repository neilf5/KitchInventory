package com.beaconcode.kitchinventory.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.beaconcode.kitchinventory.data.database.entities.User;

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

    //@Query()
}
