package com.beaconcode.kitchinventory.activities;

import android.content.Context;
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
import com.beaconcode.kitchinventory.data.database.UserRepository;
import com.beaconcode.kitchinventory.data.database.entities.User;

/**
 * BaseActivity is a class that provides common functionality for all activities in the application.
 * It handles user login state, manages the options menu, and provides a logout mechanism.
 * All activities in the application should extend this class to inherit these functionalities.
 */
public class BaseActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = "com.beaconcode.kitchinventory.MAIN_ACTIVITY_USER_ID";
    private static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.beaconcode.kitchinventory.activities.SAVED_INSTANCE_STATE_USERID_KEY";
    private static final int LOGGED_OUT = -1;

    protected UserRepository userRepository;
    protected User user;
    protected int loggedInUserId = LOGGED_OUT;

    /**
     * Called when the activity is first created.
     * Initializes the UserRepository and attempts to log in the user.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userRepository = UserRepository.getRepository(getApplication());
        loginUser(savedInstanceState);
    }

    /**
     * Attempts to log in the user using saved preferences or intent extras.
     * If the user is not logged in, starts the LoginActivity.
     * @param savedInstanceState The saved instance state bundle.
     */
    private void loginUser(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key), LOGGED_OUT);

        if (loggedInUserId == LOGGED_OUT && savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)) {
            loggedInUserId = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY, LOGGED_OUT);
        }
        if (loggedInUserId == LOGGED_OUT) {
            loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        }
        if (loggedInUserId == LOGGED_OUT) {
            startActivity(LoginActivity.loginActivityIntentFactory(getApplicationContext()));
            finish();
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

    /**
     * Returns the logged-in user's ID.
     * This should be used to determine the logged-in user's ID in child activities.
     * @return The logged-in user's ID.
     */
    protected int getLoggedInUserId() {
        return loggedInUserId;
    }

    /**
     * Called to save the current instance state.
     * Saves the logged-in user's ID to the instance state bundle.
     * @param outState The bundle to save the instance state.
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_STATE_USERID_KEY, loggedInUserId);
        updateSharedPreference();
    }

    /**
     * Updates the shared preferences with the current logged-in user's ID.
     */
    private void updateSharedPreference() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.preference_userId_key), loggedInUserId);
        sharedPrefEditor.apply();
    }

    /**
     * Shows a dialog to confirm user logout.
     * If the user confirms, logs out the user.
     */
    private void showLogoutDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(BaseActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setMessage("Would you like to logout?");
        alertBuilder.setPositiveButton("Logout", (dialog, which) -> logout());
        alertBuilder.setNegativeButton("Cancel", (dialog, which) -> alertDialog.dismiss());
        alertBuilder.create().show();
    }

    /**
     * Logs out the user by clearing the logged-in user's ID and starting the LoginActivity.
     * Clears the back stack to prevent navigation back to other activities.
     */
    private void logout() {
        loggedInUserId = LOGGED_OUT;
        updateSharedPreference();
        getIntent().putExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        Intent intent = LoginActivity.loginActivityIntentFactory(getApplicationContext());
        // Clear the back stack so the user cannot navigate back to another activity if they press the back button.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
     * Prepares the options menu before it is displayed.
     * Sets the visibility and click listeners for the menu items based on the user's state.
     * @param menu The options menu as last shown or first initialized by onCreateOptionsMenu().
     * @return true for the menu to be displayed; if false, it will not be shown.
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logout = menu.findItem(R.id.menu_logout);
        MenuItem admin = menu.findItem(R.id.admin_banner_item);
        logout.setVisible(true);

        if (user == null) {
            return false;
        }
        if (user.isAdmin()) {
            admin.setVisible(true);
        }

        logout.setTitle(user.getUsername());
        logout.setOnMenuItemClickListener(item -> {
            showLogoutDialog();
            return false;
        });
        admin.setOnMenuItemClickListener(item -> {
            Intent intent = AdminActivity.adminActivityIntentFactory(getApplicationContext());
            startActivity(intent);
            return false;
        });
        return true;
    }
}