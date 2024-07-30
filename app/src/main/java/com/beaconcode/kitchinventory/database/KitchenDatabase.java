package com.beaconcode.kitchinventory.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The Room database for KitchInventory.
 * This database contains three entities: Kitchen, User, and ShoppingList.
 * It provides DAOs for accessing these entities and manages a fixed thread pool for database operations.
 */
@Database(entities = {Kitchen.class, User.class, ShoppingList.class}, version = 1, exportSchema = false)
public abstract class KitchenDatabase extends RoomDatabase {
    public abstract KitchenDAO kitchenDAO();
    public abstract UserDAO userDAO();
    public abstract ShoppingListDAO shoppingListDAO();

    private static volatile KitchenDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /**
     * Returns the singleton instance of the KitchenDatabase.
     * If the instance is null, it initializes the database.
     * @param context the application context.
     * @return the singleton instance of KitchenDatabase.
     */
    static KitchenDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (KitchenDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            KitchenDatabase.class, "kitchen_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
