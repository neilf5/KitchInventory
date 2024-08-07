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

public class BaseActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = "com.beaconcode.kitchinventory.MAIN_ACTIVITY_USER_ID";
    private static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.beaconcode.kitchinventory.activities.SAVED_INSTANCE_STATE_USERID_KEY";
    private static final int LOGGED_OUT = -1;

    protected UserRepository userRepository;
    protected User user;
    protected int loggedInUserId = LOGGED_OUT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userRepository = UserRepository.getRepository(getApplication());
        loginUser(savedInstanceState);
    }

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

    protected int getLoggedInUserId() {
        return loggedInUserId;
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
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(BaseActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setMessage("Would you like to logout?");
        alertBuilder.setPositiveButton("Logout", (dialog, which) -> logout());
        alertBuilder.setNegativeButton("Cancel", (dialog, which) -> alertDialog.dismiss());
        alertBuilder.create().show();
    }

    private void logout() {
        loggedInUserId = LOGGED_OUT;
        updateSharedPreference();
        getIntent().putExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        startActivity(LoginActivity.loginActivityIntentFactory(getApplicationContext()));
    }

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