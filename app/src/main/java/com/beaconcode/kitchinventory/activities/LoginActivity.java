package com.beaconcode.kitchinventory.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    private ActivityLoginBinding binding;

    private UserRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = UserRepository.getRepository(getApplication());

        // Set up the click listener for the login button
        binding.btnLogin.setOnClickListener(v -> verifyUser());

        binding.btnCreateAccount.setOnClickListener(v -> startActivity(UserRegistrationActivity.userRegistrationActivityIntentFactory(getApplicationContext()))
        );
    }

    /**
     * This method verifies the entered password with the entered username against the database to see if it matches
     * If it matches it starts the main activity, if it does not it displays a toast and does not proceed.
     */

    private void verifyUser() {
        String username = binding.etUsernameLogin.getText().toString();
        if (username.isEmpty()) {
            toastMaker("Username cannot be empty.");
            return;
        }
        LiveData<User> userObserver = repository.getUserByUsername(username);
        userObserver.observe(this, user -> {
            if (user != null) {
                String password = binding.etPasswordLogin.getText().toString();
                if (password.equals(user.getPassword())) {
                    saveUserIdToPreferences(user.getUserId());
                    startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext()));
                } else {
                    toastMaker("invalid password");
                    binding.etPasswordLogin.setSelection(0);
                }
            } else {
                toastMaker(String.format("%s is not a valid username.", username));
                binding.etUsernameLogin.setSelection(0);
            }
        });
    }

    /**
     * This is saving the userID into the shared preferences so that will be kept the next time
     * the user opens the app
     * @param userId
     */

    private void saveUserIdToPreferences(int userId) {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.preference_userId_key), userId);
        editor.apply();
    }

    /**
     * Factory method to create an Intent for starting LoginActivity.
     * @param context The context from which the activity is started.
     * @return An Intent to start LoginActivity.
     */
    public static Intent loginActivityIntentFactory(Context context) {
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