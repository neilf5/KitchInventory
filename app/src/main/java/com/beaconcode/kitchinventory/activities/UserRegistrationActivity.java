package com.beaconcode.kitchinventory.activities;

import android.os.Bundle;
import android.view.View;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserInfoFromDisplay();
                insertUser();
            }
        });
    }

    private void getUserInfoFromDisplay(){
        mUsername = binding.usernameInputEditText.getText().toString();
        mPassword = binding.passwordInputEditText.getText().toString();
    }
    public void insertUser(){
        User user = new User(mUsername, mPassword);
        userRepository.insertUser(user);
        Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show();
    }
}