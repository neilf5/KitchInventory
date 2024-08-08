package com.beaconcode.kitchinventory;

import static org.junit.Assert.assertEquals;

import android.app.Application;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.beaconcode.kitchinventory.data.database.KitchenDAO;
import com.beaconcode.kitchinventory.data.database.KitchenDatabase;
import com.beaconcode.kitchinventory.data.database.KitchenRepository;
import com.beaconcode.kitchinventory.data.database.entities.Kitchen;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class KitchenTableTest {
    private KitchenDatabase db;
    private KitchenDAO kitchenDAO;
    private Kitchen kitchen1;
    private Kitchen kitchen2;

    @Before
    public void setUp() {
        kitchen1 = new Kitchen("TestFood1", 1, 1);
        kitchen2 = new Kitchen("TestFood2", 2, 2);
        Application application = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(application, KitchenDatabase.class).build();
        db.clearAllTables();
        kitchenDAO = db.kitchenDAO();
        assertEquals(0, db.userDAO().getAllUsers().size());
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void testInsertUser() {
        assertEquals(0, db.kitchenDAO().getAllKitchens().size());
        kitchenDAO.insert(kitchen1);
        List<Kitchen> listKitchen = db.kitchenDAO().getAllKitchens();
        Kitchen fetchedKitchen = listKitchen.get(0);
        assertEquals(kitchen1.getName(), fetchedKitchen.getName());
    }

    @Test
    public void testDeleteUser() {
        db.clearAllTables();
        kitchenDAO.insert(kitchen1);
        kitchenDAO.insert(kitchen2);
        assertEquals(2, db.kitchenDAO().getAllKitchens().size());
        List<Kitchen> listKitchens = db.kitchenDAO().getAllKitchens();
        Kitchen insertedKitchen1 = listKitchens.get(0);
        kitchenDAO.delete(insertedKitchen1);
        assertEquals(1, db.kitchenDAO().getAllKitchens().size());
    }

    @Test
    public void testUpdateUser() {
        kitchenDAO.insert(kitchen1);
        List<Kitchen> listKitchens = db.kitchenDAO().getAllKitchens();
        Kitchen fetchedKitchen = listKitchens.get(0);
        fetchedKitchen.setName("UpdatedName");
        kitchenDAO.update(fetchedKitchen);
        List<Kitchen> updatedListKitchens = db.kitchenDAO().getAllKitchens();
        Kitchen updatedKitchen = updatedListKitchens.get(0);
        assertEquals("UpdatedName", updatedKitchen.getName());
    }
}
