package com.beaconcode.kitchinventory;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.beaconcode.kitchinventory.activities.CookActivity;
import com.beaconcode.kitchinventory.activities.KitchenActivity;
import com.beaconcode.kitchinventory.activities.MainActivity;
import com.beaconcode.kitchinventory.activities.ShoppingListActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testMainActivityIntentFactory() {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = MainActivity.mainActivityIntentFactory(context);
        intentsTestRule.launchActivity(intent);
        intended(hasComponent(MainActivity.class.getName()));
    }

    @Test
    public void testCookActivityIntentFactory() {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = CookActivity.cookActivityIntentFactory(context);
        intentsTestRule.launchActivity(intent);
        intended(hasComponent(CookActivity.class.getName()));
    }

    @Test
    public void testKitchenActivityIntentFactory() {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = KitchenActivity.kitchenActivityIntentFactory(context);
        intentsTestRule.launchActivity(intent);
        intended(hasComponent(KitchenActivity.class.getName()));
    }

    @Test
    public void testShoppingListActivityIntentFactory() {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = ShoppingListActivity.shoppingListActivityIntentFactory(context);
        intentsTestRule.launchActivity(intent);
        intended(hasComponent(ShoppingListActivity.class.getName()));
    }
}