package com.beaconcode.kitchinventory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.beaconcode.kitchinventory.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_login);

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

    static Intent loginActivityIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}