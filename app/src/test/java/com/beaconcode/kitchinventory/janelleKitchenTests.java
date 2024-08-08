package com.beaconcode.kitchinventory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.beaconcode.kitchinventory.data.database.entities.Kitchen;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class janelleKitchenTests {
    Kitchen testKitchen;
    Kitchen otherKitchen;

    @Before
    public void setUpKitchen() {
        testKitchen = new Kitchen("Banana", 5, 2);
        otherKitchen = new Kitchen("Tangerines", 8, 6);
    }

    @After
    public void tearDownKitchen() { testKitchen = null; otherKitchen =  null; }

    @Test
    public void getKitchenName() {
        assertEquals("Banana", testKitchen.getName());
        assertEquals("Tangerines", otherKitchen.getName());
    }

    @Test
    public void setKitchenName() {
        testKitchen.setName("Chips");
        assertNotEquals("Banana", testKitchen.getName());
        testKitchen.setName("Tangerines");
        assertEquals(testKitchen.getName(), otherKitchen.getName());
    }

    @Test
    public void getQuantity() {
        assertEquals(5, testKitchen.getQuantity());
        assertEquals(8, otherKitchen.getQuantity());
    }

    @Test
    public void setQuantity() {
        testKitchen.setQuantity(8);
        assertEquals(testKitchen.getQuantity(), otherKitchen.getQuantity());
        otherKitchen.setQuantity(9);
        assertNotEquals(testKitchen.getQuantity(), otherKitchen.getQuantity());
    }
}


