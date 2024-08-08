package com.beaconcode.kitchinventory.data.database;

import androidx.lifecycle.LiveData;
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
    List<User> getAllUsers();

    @Query("SELECT * from " + KitchenDatabase.USER_TABLE + " WHERE username = :username")
    LiveData<User> getUserByUsername(String username);

    @Query("SELECT * from " + KitchenDatabase.USER_TABLE + " WHERE userId = :userId")
    LiveData<User> getUserByUserId(int userId);

    @Query("DELETE from " + KitchenDatabase.USER_TABLE)
    void deleteAll();

    @Query("DELETE from " + KitchenDatabase.USER_TABLE + " WHERE userId = :userId")
    void deleteUserByUserId(int userId);

    @Query("SELECT * from " + KitchenDatabase.USER_TABLE + " WHERE isAdmin = false")
    LiveData<List<User>> getNonAdminUsers();

}
