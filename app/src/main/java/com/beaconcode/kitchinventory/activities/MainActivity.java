package com.beaconcode.kitchinventory.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.beaconcode.kitchinventory.R;
import com.beaconcode.kitchinventory.data.database.KitchenRepository;
import com.beaconcode.kitchinventory.data.database.ShoppingListRepository;
import com.beaconcode.kitchinventory.data.database.UserRepository;
import com.beaconcode.kitchinventory.data.database.entities.User;
import com.beaconcode.kitchinventory.databinding.ActivityMainBinding;

/**
 * MainActivity class for the KitchInventory application.
 * This activity serves as the main entry point and provides navigation to other activities such as CookActivity, KitchenActivity, and ShoppingListActivity.
 * This activity is the first activity that is launched when the app is opened if the user is logged in, otherwise the LoginActivity is launched.
 */
public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private KitchenRepository kitchenRepository;
    private ShoppingListRepository shoppingListRepository;

    /**
     * Called when the activity is first created.
     * Initializes the activity and sets the content view to the main layout.
     * Sets up click listeners for navigation buttons.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        kitchenRepository = KitchenRepository.getRepository(getApplication());

        userRepository = UserRepository.getRepository(getApplication());

        shoppingListRepository = ShoppingListRepository.getRepository(getApplication());

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


        shoppingListRepository.getTotalQuantityByUserId(getLoggedInUserId()).observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer totalQuantity) {
                        if(totalQuantity!= null) {
                            String qtyString = String.valueOf(totalQuantity);
                            binding.shoppingListQty.setText("Item Qty: " + qtyString);
                        }else{
                            binding.shoppingListQty.setText("Item Qty: 0");
                        }

                    }
                });

        kitchenRepository.getTotalQuantityByUserId(getLoggedInUserId()).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer totalQuantity) {
                if(totalQuantity!= null) {
                    String qtyString = String.valueOf(totalQuantity);
                    binding.inventoryListQty.setText("Item Qty: " + qtyString);
                }else{
                    binding.inventoryListQty.setText("Item Qty: 0");
                }

            }
        });



    }

    /**
     * Factory method to create an Intent for starting MainActivity.
     *
     * @param context The context from which the activity is started.
     * @return An Intent to start MainActivity.
     */
    static Intent mainActivityIntentFactory(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }
}