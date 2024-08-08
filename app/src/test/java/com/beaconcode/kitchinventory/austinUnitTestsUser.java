package com.beaconcode.kitchinventory;

import static org.junit.Assert.assertEquals;

import com.beaconcode.kitchinventory.data.database.entities.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class austinUnitTestsUser {

    User user;

    @Before
    public void setUpUser(){
        user = new User("TestUser", "TestPW");

    }

    @After
    public void tearDownUser(){
        user = null;
    }

    @Test
    public void getUsername(){
        assertEquals("TestUser", user.getUsername());
    }

    @Test
    public void setUsername(){
        assertEquals("TestUser", user.getUsername());
        user.setUsername("DifferentUser");
        assertEquals("DifferentUser", user.getUsername());
    }
}
