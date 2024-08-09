package com.beaconcode.kitchinventory.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.beaconcode.kitchinventory.data.database.UserRepository;
import com.beaconcode.kitchinventory.data.database.entities.User;
import com.beaconcode.kitchinventory.databinding.ActivityUserRegistrationBinding;

public class UserRegistrationActivity extends AppCompatActivity {

    private ActivityUserRegistrationBinding binding;

    private UserRepository userRepository;

    String mUsername = "";
    String mPassword = "";
    String mVerify = "";
    private UserRepository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = UserRepository.getRepository(getApplication());

        binding.submitButton.setOnClickListener(v -> {
            getUserInfoFromDisplay();
            //Checks if the username field is empty
            if(mUsername.isEmpty()){
                Toast.makeText(UserRegistrationActivity.this, "Please enter a username.", Toast.LENGTH_SHORT).show();
                return;
            }
            //Checks if the password field is empty
            if(mPassword.isEmpty()){
                Toast.makeText(UserRegistrationActivity.this, "Please enter a password.", Toast.LENGTH_SHORT).show();
                return;
            }
            //Checks if the password field matches the verification password field
            if(!mVerify.equals(mPassword)){
                Toast.makeText(UserRegistrationActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                return;
            }
            insertUser();
            startActivity(LoginActivity.loginActivityIntentFactory(getApplicationContext()));
        });
    }

    /**
     * This method pulls the text from the screen to facilitate the checks correctly entered fields
     */
    private void getUserInfoFromDisplay(){
        mUsername = binding.usernameInputEditText.getText().toString();
        mPassword = binding.passwordInputEditText.getText().toString();
        mVerify = binding.passwordVerifyInputEditText.getText().toString();
    }

    /**
     * This method calls the repository's insertUser method.
     */
    public void insertUser(){
        User user = new User(mUsername, mPassword);
        repository.insertUser(user);
        Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show();
    }

    /**
     * Intent factory for userRegistration
     */
    public static Intent userRegistrationActivityIntentFactory(Context context) {
        return new Intent(context, UserRegistrationActivity.class);
    }
}