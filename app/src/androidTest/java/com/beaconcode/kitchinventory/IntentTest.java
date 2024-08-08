package com.beaconcode.kitchinventory;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.Matchers.equalTo;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.beaconcode.kitchinventory.activities.CookActivity;
import com.beaconcode.kitchinventory.activities.KitchenActivity;
import com.beaconcode.kitchinventory.activities.MainActivity;
import com.beaconcode.kitchinventory.activities.RecipeDetailsActivity;
import com.beaconcode.kitchinventory.activities.RecipesActivity;
import com.beaconcode.kitchinventory.activities.ShoppingListActivity;
import com.beaconcode.kitchinventory.activities.ShoppingListAddActivity;
import com.beaconcode.kitchinventory.activities.ShoppingListDeleteActivity;
import com.beaconcode.kitchinventory.activities.KitchenAdd;
import com.beaconcode.kitchinventory.activities.KitchenDelete;
import com.beaconcode.kitchinventory.activities.UserRegistrationActivity;
import com.beaconcode.kitchinventory.activities.LoginActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class IntentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @BeforeClass
    public static void logIn() {
        Intents.init();
        ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.et_usernameLogin)).perform(replaceText("testuser1"));
        onView(withId(R.id.et_passwordLogin)).perform(replaceText("testuser1"));
        onView(withId(R.id.btn_login)).perform(click());
        Intents.release();
    }

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
        activityScenarioRule.getScenario().onActivity(activity -> activity.startActivity(intent));
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
    public void testRecipesActivityIntentFactory() {
        Context context = ApplicationProvider.getApplicationContext();
        String foodName = "beef";
        Intent intent = RecipesActivity.recipesActivityIntentFactory(context, foodName);
        activityScenarioRule.getScenario().onActivity(activity -> activity.startActivity(intent));
        intended(hasComponent(RecipesActivity.class.getName()));
        intended(hasExtra(equalTo("com.beaconcode.kitchinventory.COOK_ACTIVITY_FOOD_NAME"), equalTo(foodName)));
    }

    @Test
    public void testRecipeDetailsActivityIntentFactory() {
        Context context = ApplicationProvider.getApplicationContext();
        String mealId = "52772";
        Intent intent = RecipeDetailsActivity.recipeDetailsIntentFactory(context, mealId);
        activityScenarioRule.getScenario().onActivity(activity -> activity.startActivity(intent));
        intended(hasComponent(RecipeDetailsActivity.class.getName()));
        intended(hasExtra(equalTo("com.beaconcode.kitchinventory.RECIPES_ACTIVITY_MEAL_ID"), equalTo(mealId)));
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

    @Test
    public void testKitchenAddActivityIntentFactory() {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = KitchenAdd.kitchenAddActivityIntentFactory(context);
        activityScenarioRule.getScenario().onActivity(activity -> activity.startActivity(intent));
        intended(hasComponent(KitchenAdd.class.getName()));
    }

    @Test
    public void testKitchenDeleteActivityIntentFactory() {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = KitchenDelete.kitchenDeleteActivitiyIntentFactory(context);
        activityScenarioRule.getScenario().onActivity(activity -> activity.startActivity(intent));
        intended(hasComponent(KitchenDelete.class.getName()));
    }

    @Test
    public void testShoppingListAddActivityIntentFactory() {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = ShoppingListAddActivity.shoppingListAddActivityIntentFactory(context);
        activityScenarioRule.getScenario().onActivity(activity -> activity.startActivity(intent));
        intended(hasComponent(ShoppingListAddActivity.class.getName()));
    }

    @Test
    public void testShoppingListDeleteActivityIntentFactory() {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = ShoppingListDeleteActivity.shoppingListDeleteActivityIntentFactory(context);
        activityScenarioRule.getScenario().onActivity(activity -> activity.startActivity(intent));
        intended(hasComponent(ShoppingListDeleteActivity.class.getName()));
    }

    @Test
    public void testUserRegistrationActivityIntentFactory() {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = UserRegistrationActivity.userRegistrationActivityIntentFactory(context);
        activityScenarioRule.getScenario().onActivity(activity -> activity.startActivity(intent));
        intended(hasComponent(UserRegistrationActivity.class.getName()));
    }
}