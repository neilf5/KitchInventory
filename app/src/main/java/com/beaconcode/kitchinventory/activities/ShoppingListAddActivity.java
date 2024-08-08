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
import com.beaconcode.kitchinventory.data.database.entities.ShoppingList;
import com.beaconcode.kitchinventory.databinding.ActivityShoppingListAddBinding;
import com.beaconcode.kitchinventory.databinding.ActivityShoppingListBinding;
import com.beaconcode.kitchinventory.ui.view.CookInterface;

import java.util.ArrayList;

public class ShoppingListAddActivity extends BaseActivity {

    private ActivityShoppingListAddBinding binding;
    private ShoppingListRepository shoppingListRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityShoppingListAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        shoppingListRepository = ShoppingListRepository.getRepository(getApplication());

        binding.addItemButton.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.itemName.getText().toString();
                int quantity = 0;
                try {
                    quantity = Integer.parseInt(binding.itemQuantity.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(ShoppingListAddActivity.this, "Plz enter a valid number", Toast.LENGTH_SHORT).show();;
                }

                if (quantity > 0) {
                    //UPDATED THIS LINE TO INCLUDE NEW USERID PARAMETER
                    ShoppingList item = new ShoppingList(name, quantity, getLoggedInUserId());
                    shoppingListRepository.insertShoppingList(item);
                    Toast.makeText(ShoppingListAddActivity.this, item.getName() + " added to shopping list", Toast.LENGTH_SHORT).show();
                }
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

    public static Intent shoppingListAddActivityIntentFactory(Context context) {
        return new Intent(context, ShoppingListAddActivity.class);
    }

}