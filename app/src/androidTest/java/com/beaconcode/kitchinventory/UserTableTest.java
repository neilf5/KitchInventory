package com.beaconcode.kitchinventory;

import static org.junit.Assert.assertEquals;

import android.app.Application;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.beaconcode.kitchinventory.data.database.KitchenDatabase;
import com.beaconcode.kitchinventory.data.database.KitchenRepository;
import com.beaconcode.kitchinventory.data.database.ShoppingListRepository;
import com.beaconcode.kitchinventory.data.database.UserDAO;
import com.beaconcode.kitchinventory.data.database.UserRepository;
import com.beaconcode.kitchinventory.data.database.entities.User;

import java.util.List;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UserTableTest {
    private UserRepository userRepository;
    private KitchenRepository kitchenRepository;
    private ShoppingListRepository shoppingListRepository;
    private KitchenDatabase db;
    private UserDAO userDAO;
    private User user1;
    private User user2;

    @Before
    public void setUp() {
        Application application = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(application, KitchenDatabase.class).build();
        db.clearAllTables();
        userDAO = db.userDAO();
        user1 = new User("TestingUser1", "TestingPassword1");
        user2 = new User("TestingUser2", "TestingPassword2");
        assertEquals(0, db.userDAO().getAllUsers().size());
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void testInsertUser() {
        userDAO.insert(user1);
        List<User> listUsers = db.userDAO().getAllUsers();
        User fetchedUser = listUsers.get(0);
        assertEquals(user1.getUsername(), fetchedUser.getUsername());
        }
}