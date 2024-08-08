package com.beaconcode.kitchinventory;

import com.beaconcode.kitchinventory.data.database.entities.ShoppingList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ariShoppingListTests {
    ShoppingList shoppingList;

    @Before
    public void setUpShoppingList(){
        shoppingList = new ShoppingList("init", 1, 1);
    }

    @After
    public void tearDownList(){ shoppingList = null;}

    @Test
    public void getItem(){
        assertEquals(shoppingList.getName(), "init");
    }

    @Test
    public void setName(){
        shoppingList.setName("test");
        assertEquals(shoppingList.getName(), "test");
    }

    @Test
    public void quantityTest(){
        shoppingList.setQuantity(66);
        assertEquals(shoppingList.getQuantity(), 66);
    }

}