package com.beaconcode.kitchinventory.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.beaconcode.kitchinventory.R;
import com.beaconcode.kitchinventory.data.database.ShoppingListRepository;
import com.beaconcode.kitchinventory.databinding.ActivityShoppingListAddBinding;
import com.beaconcode.kitchinventory.databinding.ActivityShoppingListDeleteBinding;

public class ShoppingListDeleteActivity extends AppCompatActivity {

    private ActivityShoppingListDeleteBinding binding;
    private ShoppingListRepository shoppingListRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityShoppingListDeleteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        shoppingListRepository = ShoppingListRepository.getRepository(getApplication());

        binding.deleteItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.itemName.getText().toString();

                    shoppingListRepository.deleteByFoodName(name);
                    Toast.makeText(ShoppingListDeleteActivity.this, name + " removed from shopping list", Toast.LENGTH_SHORT).show();
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intnet = ShoppingListActivity.shoppingListActivityIntentFactory(getApplicationContext());
                startActivity(intnet);
            }
        });




    }

    public static Intent shoppingListDeleteActivityIntentFactory(Context context) {
        return new Intent(context, ShoppingListDeleteActivity.class);
    }
}