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

import com.beaconcode.kitchinventory.data.database.ShoppingListDAO;
import com.beaconcode.kitchinventory.data.database.entities.ShoppingList;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ShoppingListTableTest {
    private KitchenDatabase db;
    private ShoppingListDAO shoppingListDAO;
    private ShoppingList shoppingList1;
    private ShoppingList shoppingList2;

    @Before
    public void setUp() {
        shoppingList1 = new ShoppingList("Red Pepper", 1, 1);
        shoppingList2 = new ShoppingList("Gorgonzola", 1, 1);
        Application application = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(application, KitchenDatabase.class).build();
        db.clearAllTables();
        shoppingListDAO = db.shoppingListDAO();
        assertEquals(0, shoppingListDAO.getAllShoppingList().size());
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void testInsertShoppingList() {
        assertEquals(0, shoppingListDAO.getAllShoppingList().size());
        shoppingListDAO.insert(shoppingList1);
        List<ShoppingList> listShoppingList = shoppingListDAO.getAllShoppingList();
        ShoppingList fetchedShoppingList = listShoppingList.get(0);
        assertEquals(shoppingList1.getName(), fetchedShoppingList.getName());
    }

    @Test
    public void testDeleteShoppingList() {
        db.clearAllTables();
        shoppingListDAO.insert(shoppingList1);
        shoppingListDAO.insert(shoppingList2);
        assertEquals(2, shoppingListDAO.getAllShoppingList().size());
        List<ShoppingList> listShoppingList = shoppingListDAO.getAllShoppingList();
        ShoppingList insertedShoppingList = listShoppingList.get(0);
        shoppingListDAO.delete(insertedShoppingList);
        assertEquals(1, shoppingListDAO.getAllShoppingList().size());
    }

    @Test
    public void testUpdateShoppingList() {
        shoppingListDAO.insert(shoppingList1);
        List<ShoppingList> listShoppingList = shoppingListDAO.getAllShoppingList();
        ShoppingList fetchedShoppingList = listShoppingList.get(0);
        fetchedShoppingList.setName("UpdatedShoppingListName");
        shoppingListDAO.update(fetchedShoppingList);
        List<ShoppingList> updatedShoppingList = shoppingListDAO.getAllShoppingList();
        ShoppingList updatedShoppingListEntry = updatedShoppingList.get(0);
        assertEquals("UpdatedShoppingListName", updatedShoppingListEntry.getName());
    }

}
