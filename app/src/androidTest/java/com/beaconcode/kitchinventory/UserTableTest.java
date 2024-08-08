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
    private KitchenDatabase db;
    private UserDAO userDAO;
    private User user1;
    private User user2;

    @Before
    public void setUp() {
        user1 = new User("TestingUser1", "TestingPassword1");
        user2 = new User("TestingUser2", "TestingPassword2");
        Application application = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(application, KitchenDatabase.class).build();
        db.clearAllTables();
        userDAO = db.userDAO();
        assertEquals(0, db.userDAO().getAllUsers().size());
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void testInsertUser() {
        assertEquals(0, db.userDAO().getAllUsers().size());
        userDAO.insert(user1);
        List<User> listUsers = db.userDAO().getAllUsers();
        User fetchedUser = listUsers.get(0);
        assertEquals(user1.getUsername(), fetchedUser.getUsername());
    }

    @Test
    public void testDeleteUser() {
        db.clearAllTables();
        userDAO.insert(user1);
        userDAO.insert(user2);
        assertEquals(2, db.userDAO().getAllUsers().size());
        List<User> listUsers = db.userDAO().getAllUsers();
        User insertedUser1 = listUsers.get(0);
        userDAO.delete(insertedUser1);
        assertEquals(1, db.userDAO().getAllUsers().size());
    }

    @Test
    public void testUpdateUser() {
        userDAO.insert(user1);
        List<User> listUsers = db.userDAO().getAllUsers();
        User fetchedUser = listUsers.get(0);
        fetchedUser.setUsername("UpdatedUsername");
        userDAO.update(fetchedUser);
        List<User> updatedListUsers = db.userDAO().getAllUsers();
        User updatedUser = updatedListUsers.get(0);
        assertEquals("UpdatedUsername", updatedUser.getUsername());
    }

}
