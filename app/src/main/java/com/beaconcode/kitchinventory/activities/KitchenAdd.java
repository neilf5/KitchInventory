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
        setContentView(binding.getRoot());

        binding.addToKitchenButton.setOnClickListener(v -> {
            String enteredFoodItem = binding.userShopListInput.getText().toString();

            Toast.makeText(KitchenAdd.this, "Why did you enter " + enteredFoodItem, Toast.LENGTH_SHORT).show();
            /*  //causes crash, maybe because there's nothing in shoppinglist table?

                if (shoppingListRepository.getAllShoppingList().size() <= 0) {
                Toast.makeText(KitchenAdd.this, "EMPTY", Toast.LENGTH_SHORT).show();
            }

            for (ShoppingList shoppinglist : shoppingListRepository.getAllShoppingList()) {
                if (shoppinglist.getName().equals(enteredFoodItem) && getLoggedInUserId() == shoppinglist.getUserId()) {
                    Toast.makeText(KitchenAdd.this, "Food found! Adding to your list!", Toast.LENGTH_SHORT).show();

                    //add found food from shopping list over to kitchen's list
                    Kitchen kitchen = new Kitchen(shoppinglist.getName(), shoppinglist.getQuantity(), shoppinglist.getUserId());
                    kitchenRepository.insertKitchen(kitchen); //add new kitchen over to database
                    shoppingListRepository.clearShoppingListByUserId(shoppinglist.getUserId()); //remove the item from shopping list
                     return;
                }

                Toast.makeText(KitchenAdd.this, "Food item not found in your shopping list D:", Toast.LENGTH_SHORT).show();
            }
             */

        });


        binding.goToShoppingListButton.setOnClickListener(v -> {
            Intent intent = ShoppingListActivity.shoppingListActivityIntentFactory(getApplicationContext());
            startActivity(intent);
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }


    static Intent kitchenAddActivityIntentFactory(Context context) {
        return new Intent(context, KitchenAdd.class);
    }
}