package com.beaconcode.kitchinventory.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.beaconcode.kitchinventory.R;
import com.beaconcode.kitchinventory.data.database.KitchenRepository;
import com.beaconcode.kitchinventory.data.database.ShoppingListRepository;
import com.beaconcode.kitchinventory.data.database.entities.Kitchen;
import com.beaconcode.kitchinventory.data.database.entities.ShoppingList;
import com.beaconcode.kitchinventory.databinding.ActivityKitchenAddBinding;

public class KitchenAdd extends BaseActivity {

    private ActivityKitchenAddBinding binding;
    private ShoppingListRepository shoppingListRepository;
    private KitchenRepository kitchenRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKitchenAddBinding.inflate(getLayoutInflater());
        shoppingListRepository = ShoppingListRepository.getRepository(getApplication());
        kitchenRepository = KitchenRepository.getRepository(getApplication());
        Intent intent = KitchenActivity.kitchenActivityIntentFactory(getApplicationContext());

        setContentView(binding.getRoot());

        //add from shopping list, doesn't work
        binding.addFromShoppingListButton.setOnClickListener(v -> {

            String enteredFoodItem = binding.userShopListInput.getText().toString();


                if (shoppingListRepository.getAllShoppingList().isEmpty()) {
                    Toast.makeText(KitchenAdd.this, "EMPTY", Toast.LENGTH_SHORT).show();
            }

            for (ShoppingList shoppinglist : shoppingListRepository.getAllShoppingList()) {
                if (shoppinglist.getName().equals(enteredFoodItem) && getLoggedInUserId() == shoppinglist.getUserId()) {


                    //add found food from shopping list over to kitchen's list
                    Kitchen kitchen = new Kitchen(shoppinglist.getName(), shoppinglist.getQuantity(), getLoggedInUserId());
                    kitchenRepository.insertKitchen(kitchen); //add new kitchen over to database
                    shoppingListRepository.clearShoppingListByUserId(shoppinglist.getUserId()); //remove the item from shopping list
                    Toast.makeText(KitchenAdd.this, "Food added to your KitchInventory!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    break;

                } else {
                    Toast.makeText(KitchenAdd.this, "Food could not be added", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //add on your own
        binding.addToKitchenButton.setOnClickListener(v -> {
            String enteredFoodItem = binding.userShopListInput.getText().toString();
            int enteredQuantity = 0;

            try {
                enteredQuantity = Integer.parseInt(binding.userShopListQuantityInput.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(KitchenAdd.this, "Invalid number", Toast.LENGTH_SHORT).show();
            }

            //negative or 0
            if (enteredQuantity <= 0) {
                Toast.makeText(KitchenAdd.this, "Quantity cannot be less than or equal to 0", Toast.LENGTH_SHORT).show();
            } else if (enteredFoodItem.isEmpty()) {
                Toast.makeText(KitchenAdd.this, "Food item not found", Toast.LENGTH_SHORT).show();
            } else {
                Kitchen newKitchenItem = new Kitchen(enteredFoodItem, enteredQuantity, getLoggedInUserId());
                kitchenRepository.insertKitchen(newKitchenItem);
                Toast.makeText(KitchenAdd.this, "Added new food item to your KitchInventory!", Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }

        });

        binding.goToShoppingListButton.setOnClickListener(v -> {
            Intent intentShop = ShoppingListActivity.shoppingListActivityIntentFactory(getApplicationContext());
            startActivity(intentShop);
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }


    public static Intent kitchenAddActivityIntentFactory(Context context) {
        return new Intent(context, KitchenAdd.class);
    }
}