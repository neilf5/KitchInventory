package com.beaconcode.kitchinventory.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.beaconcode.kitchinventory.R;
import com.beaconcode.kitchinventory.data.database.KitchenRepository;
import com.beaconcode.kitchinventory.data.database.ShoppingListRepository;
import com.beaconcode.kitchinventory.data.database.UserRepository;
import com.beaconcode.kitchinventory.data.database.entities.User;
import com.beaconcode.kitchinventory.databinding.ActivityMainBinding;

/**
 * MainActivity class for the KitchInventory application.
 * This activity serves as the main entry point and provides navigation to other activities such as CookActivity, KitchenActivity, and ShoppingListActivity.
 * This activity is the first activity that is launched when the app is opened if the user is logged in, otherwise the LoginActivity is launched.
 */
public class MainActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = "com.beaconcode.kitchinventory.MAIN_ACTIVITY_USER_ID";

    private static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.beaconcode.kitchinventory.activities.SAVED_INSTANCE_STATE_USERID_KEY";

    private ActivityMainBinding binding;

    private KitchenRepository kitchenRepository;

    private UserRepository userRepository;

    private ShoppingListRepository shoppingListRepository;

    private static final int LOGGED_OUT = -1;

    private User user;

    private int loggedInUserId = LOGGED_OUT;

    /**
     * Called when the activity is first created.
     * Initializes the activity and sets the content view to the main layout.
     * Sets up click listeners for navigation buttons.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        kitchenRepository = KitchenRepository.getRepository(getApplication());

        userRepository = UserRepository.getRepository(getApplication());

        shoppingListRepository = ShoppingListRepository.getRepository(getApplication());
        loginUser(savedInstanceState);


        if (loggedInUserId == LOGGED_OUT) {
            Intent intent = LoginActivity.loginActivityIntentFactory(getApplicationContext());
            startActivity(intent);
        }

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

    private void loginUser(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);

        loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key), LOGGED_OUT);

        if (loggedInUserId == LOGGED_OUT & savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)) {
            loggedInUserId = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY, LOGGED_OUT);
        }
        if (loggedInUserId == LOGGED_OUT) {
            loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        }
        if (loggedInUserId == LOGGED_OUT) {
            return;
        }
        LiveData<User> userObserver = userRepository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, user -> {
            this.user = user;
            if (user != null) {
                invalidateOptionsMenu();
            }
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_STATE_USERID_KEY, loggedInUserId);
        updateSharedPreference();
    }

    private void updateSharedPreference() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.preference_userId_key), loggedInUserId);
        sharedPrefEditor.apply();
    }

    private void showLogoutDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setMessage("Would you like to logout?");

        alertBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertBuilder.create().show();
    }

    private void logout() {

        loggedInUserId = LOGGED_OUT;
        updateSharedPreference();
        getIntent().putExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);

        startActivity(LoginActivity.loginActivityIntentFactory(getApplicationContext()));
    }


    /**
     * Initializes the contents of the Activity's standard options menu.
     *
     * @param menu The options menu in which you place your items.
     * @return true for the menu to be displayed; if false, it will not be shown.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logout = menu.findItem(R.id.menu_logout);
        MenuItem admin = menu.findItem(R.id.admin_banner_item);
        logout.setVisible(true);

        if (user == null) {
            return false;
        }
        if(user.isAdmin()){
            admin.setVisible(true);
        }

        logout.setTitle(user.getUsername());
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                showLogoutDialog();
                return false;
            }
        });
       /* admin.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                //startActivity(TODO IMPLEMENT INTENT FACTORY FOR ADMIN PAGE);
                return false;
            }
        });*/
        return true;
    }

    /**
     * Factory method to create an Intent for starting MainActivity.
     *
     * @param context The context from which the activity is started.
     * @param userId  The user ID to be passed to the activity.
     * @return An Intent to start MainActivity.
     */
    static Intent mainActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
}