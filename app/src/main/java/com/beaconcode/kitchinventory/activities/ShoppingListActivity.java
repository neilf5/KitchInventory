package com.beaconcode.kitchinventory.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.beaconcode.kitchinventory.R;

/**
 * This activity will display the current items in the user's shopping list from the database.
 */
public class ShoppingListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        }

    /**
     * Initialize the contents of the Activity's standard options menu.
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed; if you return false it will not be shown.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    /**
     * Factory method to create an Intent for ShoppingListActivity.
     * @param context The context from which the activity is started.
     * @return An Intent to start ShoppingListActivity.
     */
    static Intent shoppingListActivityIntentFactory(Context context) {
        return new Intent(context, ShoppingListActivity.class);
    }
}