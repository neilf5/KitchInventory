package com.beaconcode.kitchinventory.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.beaconcode.kitchinventory.R;
import com.beaconcode.kitchinventory.data.database.UserRepository;
import com.beaconcode.kitchinventory.data.database.entities.User;
import com.beaconcode.kitchinventory.databinding.ActivityLoginBinding;

/**
 * Activity class for handling user login.
 * This activity provides the interface for user login and verifies user credentials.
 */
public class LoginActivity extends AppCompatActivity {

    private UserRepository userRepository;

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
    }

    /**
     * Verifies the user credentials entered in the login form.
     * If the user is valid, the user is navigated to the MainActivity.
     */
    private void verifyUser() {
        String username = binding.etUsernameLogin.getText().toString();
        if (username.isEmpty()) {
            toastMaker("Username cannot be empty.");
        }
        LiveData<User> userObserver = userRepository.getUserByUsername(username);
        userObserver.observe(this, user -> {
            if (user != null) {
                String password = binding.etPasswordLogin.getText().toString();
                if (password.equals(user.getPassword())) {
                    Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId());
                    startActivity(intent);
                } else {
                    toastMaker("Invalid password.");
                }
            } else {
                toastMaker(String.format("User %s not a valid username", username));
                binding.etUsernameLogin.setSelection(0, username.length());
            }
        });
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