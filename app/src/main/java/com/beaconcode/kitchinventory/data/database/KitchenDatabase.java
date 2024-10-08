package com.beaconcode.kitchinventory.data.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
@Database(entities = {Kitchen.class, User.class, ShoppingList.class}, version = 2, exportSchema = false)
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
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i("db", "DATABASE CREATED!");
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDAO();
                dao.deleteAll();
                User admin = new User("admin1", "admin1");
                admin.setAdmin(true);
                dao.insert(admin);

                User testUser1 = new User("testuser1", "testuser1");
                dao.insert(testUser1);
                User testUser2 = new User("testuser2", "testuser2");
                dao.insert(testUser2);
                User testUser3 = new User("testuser3", "testuser3");
                dao.insert(testUser3);

            });
        }
    };
}
