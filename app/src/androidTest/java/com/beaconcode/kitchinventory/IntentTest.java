package com.beaconcode.kitchinventory;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
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
public class IntentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

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
        // Fill in the login fields
        onView(withId(R.id.et_usernameLogin)).perform(replaceText("testuser1"));
        onView(withId(R.id.et_passwordLogin)).perform(replaceText("testuser1"));
        // Click the login button, starts the main activity using the main activity intent factory
        onView(withId(R.id.btn_login)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }

    @Test
    public void testCookActivityIntentFactory() {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = CookActivity.cookActivityIntentFactory(context);
        activityScenarioRule.getScenario().onActivity(activity -> activity.startActivity(intent));
        intended(hasComponent(CookActivity.class.getName()));
    }

    @Test
    public void testKitchenActivityIntentFactory() {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = KitchenActivity.kitchenActivityIntentFactory(context);
        activityScenarioRule.getScenario().onActivity(activity -> activity.startActivity(intent));
        intended(hasComponent(KitchenActivity.class.getName()));
    }

    @Test
    public void testShoppingListActivityIntentFactory() {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = ShoppingListActivity.shoppingListActivityIntentFactory(context);
        activityScenarioRule.getScenario().onActivity(activity -> activity.startActivity(intent));
        intended(hasComponent(ShoppingListActivity.class.getName()));
    }
}