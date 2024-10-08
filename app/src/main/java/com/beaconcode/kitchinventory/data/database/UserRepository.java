package com.beaconcode.kitchinventory.data.database;

import android.app.Application;
import android.util.Log;


import androidx.lifecycle.LiveData;

import com.beaconcode.kitchinventory.data.database.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class UserRepository {
    private UserDAO userDAO;

    private ArrayList<User> allLogs;

    private static UserRepository repository;

    private UserRepository(Application application){
        KitchenDatabase db = KitchenDatabase.getDatabase(application);
        this.userDAO = db.userDAO();
        this.allLogs = (ArrayList<User>) this.userDAO.getAllUsers();

    }

    public static UserRepository getRepository(Application application){
        if (repository != null){
            return repository;
        }
        Future<UserRepository> future = KitchenDatabase.databaseWriteExecutor.submit(
                new Callable<UserRepository>() {
                    @Override
                    public UserRepository call() throws Exception {
                        return new UserRepository(application);
                    }
                }
        );
        try{
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            Log.d("DB","Problem getting UserRepository, thread error.");
        }
        return null;
    }

    public ArrayList<User> getAllUsers() {
        Future<ArrayList<User>> future = KitchenDatabase.databaseWriteExecutor.submit(
                () -> (ArrayList<User>) userDAO.getAllUsers()
        );
        try{
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            Log.i("DB", "Problem when getting all Users in the repository");
        }
        return null;
    }

    public void insertUser(User user){
        KitchenDatabase.databaseWriteExecutor.execute(()->
        {
            userDAO.insert(user);
        });
    }

    public LiveData<User> getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    public LiveData<User> getUserByUserId(int userId) {
        return userDAO.getUserByUserId(userId);
    }

    public LiveData<List<User>> getNonAdminUsers() {
        return userDAO.getNonAdminUsers();
    }

    public void deleteUserByUserId(int userId) {
        KitchenDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.deleteUserByUserId(userId);
        });
    }

}
