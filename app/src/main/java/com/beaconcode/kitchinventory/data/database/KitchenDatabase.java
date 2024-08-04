package com.beaconcode.kitchinventory.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.beaconcode.kitchinventory.data.database.entities.User;
import com.beaconcode.kitchinventory.data.database.entities.Kitchen;
import com.beaconcode.kitchinventory.data.database.entities.ShoppingList;

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

    public static final String DATABASE_NAME = "kitchen_database";
    public static final String KITCHEN_TABLE = "kitchen_table";
    public static final String USER_TABLE = "user_table";
    public static final String SHOPPING_LIST_TABLE = "shopping_list_table";

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
                            KitchenDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
