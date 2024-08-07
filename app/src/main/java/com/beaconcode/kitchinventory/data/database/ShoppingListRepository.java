package com.beaconcode.kitchinventory.data.database;

import android.app.Application;
import android.util.Log;

import com.beaconcode.kitchinventory.data.database.entities.Kitchen;
import com.beaconcode.kitchinventory.data.database.entities.ShoppingList;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ShoppingListRepository {
    private ShoppingListDAO shoppingListDAO;

    private ArrayList<ShoppingList> allLogs;

    private static ShoppingListRepository repository;

    private ShoppingListRepository(Application application){
        KitchenDatabase db = KitchenDatabase.getDatabase(application);
        this.shoppingListDAO = db.shoppingListDAO();
        this.allLogs = (ArrayList<ShoppingList>) this.shoppingListDAO.getAllRecords();

    }

    public static ShoppingListRepository getRepository(Application application){
        if (repository != null){
            return repository;
        }
        Future<ShoppingListRepository> future = KitchenDatabase.databaseWriteExecutor.submit(
                new Callable<ShoppingListRepository>() {
                    @Override
                    public ShoppingListRepository call() throws Exception {
                        return new ShoppingListRepository(application);
                    }
                }
        );
        try{
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            Log.d("DB","Problem getting ShoppingListRepository, thread error.");
        }
        return null;
    }

    public ArrayList<ShoppingList> getAllLogs() {
        Future<ArrayList<ShoppingList>> future = KitchenDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<ShoppingList>>() {
                    @Override
                    public ArrayList<ShoppingList> call() throws Exception {
                        return (ArrayList<ShoppingList>) shoppingListDAO.getAllRecords();
                    }
                }
        );
        try{
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            Log.i("DB", "Problem when getting all Shopping Lists in the repository");
        }
        return null;
    }

    public void insertShoppingList(ShoppingList shoppingList){
        KitchenDatabase.databaseWriteExecutor.execute(()->
        {
            shoppingListDAO.insert(shoppingList);
        });
    }

    public void clearShoppingListByUserId(int userId){
        KitchenDatabase.databaseWriteExecutor.execute(()->
        {
            shoppingListDAO.clearShoppingListByUserId(userId);
        });
    }
}
