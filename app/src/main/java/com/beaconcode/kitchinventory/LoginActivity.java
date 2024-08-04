package com.beaconcode.kitchinventory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.beaconcode.kitchinventory.databinding.ActivityLoginBinding;

/**
 * Activity class for handling user login.
 * This activity provides the interface for user login and verifies user credentials.
 */
public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_login);

        // Set up the click listener for the login button
        binding.btnLogin.setOnClickListener(v -> {
            verifyUser();
        });
    };

    //TODO: Need to pull user data from the database and also implement datastore to keep track of user login state

    private void verifyUser() {
        String username = binding.etUsernameLogin.getText().toString();
        if (username.isEmpty()) {
            toastMaker("Username cannot be empty.");
            return;
        }
    }

    /**
     * Factory method to create an Intent for starting LoginActivity.
     * @param context The context from which the activity is started.
     * @return An Intent to start LoginActivity.
     */
    static Intent loginActivityIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    /**
     * Displays a toast message with the specified text.
     * @param message The message to be displayed in the toast.
     */
    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}