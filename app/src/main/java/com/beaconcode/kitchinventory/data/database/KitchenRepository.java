package com.beaconcode.kitchinventory.data.database;

import android.app.Application;
import android.util.Log;

import com.beaconcode.kitchinventory.data.database.entities.Kitchen;

import java.util.ArrayList;
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

    public void insertGymLog(Kitchen kitchen){
        KitchenDatabase.databaseWriteExecutor.execute(()->
        {
            kitchenDAO.insert(kitchen);
        });
    }
}
