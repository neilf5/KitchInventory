package com.beaconcode.kitchinventory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.beaconcode.kitchinventory.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = "com.beaconcode.kitchinventory.MAIN_ACTIVITY_USER_ID";

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    static Intent mainActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
}