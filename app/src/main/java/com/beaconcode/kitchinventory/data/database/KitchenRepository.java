package com.beaconcode.kitchinventory.data.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.beaconcode.kitchinventory.data.database.entities.Kitchen;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class KitchenRepository {
    private KitchenDAO kitchenDAO;

    private ArrayList<Kitchen> allLogs;

    private static KitchenRepository repository;

    private KitchenRepository(Application application){
        KitchenDatabase db = KitchenDatabase.getDatabase(application);
        this.kitchenDAO = db.kitchenDAO();
        this.allLogs = (ArrayList<Kitchen>) this.kitchenDAO.getAllRecords();

    }

    public static KitchenRepository getRepository(Application application){
        if (repository != null){
            return repository;
        }
        Future<KitchenRepository> future = KitchenDatabase.databaseWriteExecutor.submit(
                new Callable<KitchenRepository>() {
                    @Override
                    public KitchenRepository call() throws Exception {
                        return new KitchenRepository(application);
                    }
                }
        );
        try{
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            Log.d("DB","Problem getting KitchenRepository, thread error.");
        }
        return null;
    }

    //Doesn't work
    public void delete(Kitchen kitchen)
    {
        kitchenDAO.delete(kitchen);
    }

    public void deleteByFoodName(String foodName){
        KitchenDatabase.databaseWriteExecutor.execute(()->
        {
            kitchenDAO.deleteByFoodName(foodName);
        });
    }

    public void deleteByQuantityName(Integer quantityName){
        KitchenDatabase.databaseWriteExecutor.execute(()->
        {
            kitchenDAO.deleteByQuantityName(quantityName);
        });
    }

    public ArrayList<Kitchen> getAllLogs() {
        Future<ArrayList<Kitchen>> future = KitchenDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<Kitchen>>() {
                    @Override
                    public ArrayList<Kitchen> call() throws Exception {
                        return (ArrayList<Kitchen>) kitchenDAO.getAllRecords();
                    }
                }
        );
        try{
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            Log.i("DB", "Problem when getting all Kitchens in the repository");
        }
        return null;
    }


    public LiveData<List<String>> getFoodList(){
        return kitchenDAO.getFoodList();
    }

    public LiveData<List<Integer>> getQuantityList(){
        return kitchenDAO.getQuantityList();
    }

    public void insertKitchen(Kitchen kitchen){
        KitchenDatabase.databaseWriteExecutor.execute(()->
        {
            kitchenDAO.insert(kitchen);
        });
    }


    public void clearKitchenByUserId(int userId){
        KitchenDatabase.databaseWriteExecutor.execute(()->
        {
            kitchenDAO.clearKitchenByUserId(userId);
        });
    }

    public LiveData<Integer> getTotalQuantityByUserId(int userId) {
        return kitchenDAO.getTotalQuantityByUserId(userId);
    }
}
