package com.beaconcode.kitchinventory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.beaconcode.kitchinventory.databinding.ActivityMainBinding;

/**
 * MainActivity class for the KitchInventory application.
 * This activity serves as the main entry point and provides navigation to other activities such as CookActivity, KitchenActivity, and ShoppingListActivity.
 * This activity is the first activity that is launched when the app is opened if the user is logged in, otherwise the LoginActivity is launched.
 */
public class MainActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = "com.beaconcode.kitchinventory.MAIN_ACTIVITY_USER_ID";

    private ActivityMainBinding binding;

    /**
     * Called when the activity is first created.
     * Initializes the activity and sets the content view to the main layout.
     * Sets up click listeners for navigation buttons.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCook.setOnClickListener(v -> {
            Intent intent = CookActivity.cookActivityIntentFactory(getApplicationContext());
            startActivity(intent);
        });

        binding.btnKitchen.setOnClickListener(v -> {
            Intent intent = KitchenActivity.kitchenActivityIntentFactory(getApplicationContext());
            startActivity(intent);
        });

        binding.btnShoppingList.setOnClickListener(v -> {
            Intent intent = ShoppingListActivity.shoppingListActivityIntentFactory(getApplicationContext());
            startActivity(intent);
        });
    }

    /**
     * Initializes the contents of the Activity's standard options menu.
     * @param menu The options menu in which you place your items.
     * @return true for the menu to be displayed; if false, it will not be shown.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    /**
     * Factory method to create an Intent for starting MainActivity.
     * @param context The context from which the activity is started.
     * @param userId The user ID to be passed to the activity.
     * @return An Intent to start MainActivity.
     */
    static Intent mainActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
}