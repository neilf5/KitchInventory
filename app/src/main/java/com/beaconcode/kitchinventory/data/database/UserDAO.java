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

    /**
     *inserts User(s) into the User table
     */
    @Insert
    void insert(User... users);

    /**
     *Updates Users in the User table
     */
    @Update
    void update(User... users);

    /**
     *Deletes User(s) from the User table
     */
    @Delete
    void delete(User users);

    /**
     *Selects all users from the User Table
     */
    @Query("SELECT * from " + KitchenDatabase.USER_TABLE)
    List<User> getAllUsers();

    /**
     *Selects all users from the User Table with a matching username
     */
    @Query("SELECT * from " + KitchenDatabase.USER_TABLE + " WHERE username = :username")
    LiveData<User> getUserByUsername(String username);

    /**
     *Selects all users from the User Table with a matching user ID
     */
    @Query("SELECT * from " + KitchenDatabase.USER_TABLE + " WHERE userId = :userId")
    LiveData<User> getUserByUserId(int userId);

    /**
     *Deletes everything from the User Table
     */
    @Query("DELETE from " + KitchenDatabase.USER_TABLE)
    void deleteAll();

    /**
     *Deletes users from the User Table with matching user Id
     */
    @Query("DELETE from " + KitchenDatabase.USER_TABLE + " WHERE userId = :userId")
    void deleteUserByUserId(int userId);

    /**
     *Selects users from the User Table who are not admins
     */
    @Query("SELECT * from " + KitchenDatabase.USER_TABLE + " WHERE isAdmin = false")
    LiveData<List<User>> getNonAdminUsers();

}
