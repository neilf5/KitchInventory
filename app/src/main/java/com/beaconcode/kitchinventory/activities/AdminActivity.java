package com.beaconcode.kitchinventory.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.beaconcode.kitchinventory.R;
import com.beaconcode.kitchinventory.data.database.KitchenRepository;
import com.beaconcode.kitchinventory.data.database.ShoppingListRepository;
import com.beaconcode.kitchinventory.data.database.UserRepository;
import com.beaconcode.kitchinventory.data.database.entities.User;
import com.beaconcode.kitchinventory.databinding.ActivityAdminBinding;

public class AdminActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ActivityAdminBinding binding;
    private UserRepository userRepository;
    private KitchenRepository kitchenRepository;
    private ShoppingListRepository shoppingListRepository;
    private String selectedUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userRepository = UserRepository.getRepository(getApplication());

        // Set up the spinner
        Spinner spinner = findViewById(R.id.spn_users);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Get all users from the database
        userRepository.getNonAdminUsers().observe(this, users -> {
            adapter.clear();
            for (User user : users) {
                adapter.add(user.getUsername());
            }
            adapter.notifyDataSetChanged();
        });

        // Set up the delete button
        binding.btnDeleteUser.setOnClickListener(v -> {
            if (selectedUsername != null) {
                userRepository.deleteUserByUsername(selectedUsername);
            }
        });

        // Set up clear shopping list button


    }

    /**
     * Factory method to create an Intent to start this activity.
     * @param context
     * @return Intent to start this activity.
     */
    static Intent adminActivityIntentFactory(Context context) {
        return new Intent(context, AdminActivity.class);
    }

    /**
     * Called when an item in the Spinner is selected.
     * Fetches the selected user's details from the repository.
     * @param parent The AdapterView where the selection happened.
     * @param view The view within the AdapterView that was clicked.
     * @param position The position of the view in the adapter.
     * @param id The row id of the item that is selected.
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Get the selected user
        String username = parent.getItemAtPosition(position).toString();
        userRepository.getUserByUsername(username).observe(this, user -> {
            selectedUsername = user.getUsername();
        });
    }

    /**
     * Called when the Spinner is not selected. Not needed for this application but required by the interface.
     * @param parent The AdapterView where the selection happened.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing.
        }
}