package com.beaconcode.kitchinventory.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import com.beaconcode.kitchinventory.R;
import com.beaconcode.kitchinventory.data.database.KitchenRepository;
import com.beaconcode.kitchinventory.data.database.entities.Kitchen;
import com.beaconcode.kitchinventory.databinding.ActivityKitchenDeleteBinding;


public class KitchenDelete extends BaseActivity {


    private ActivityKitchenDeleteBinding binding;
    private KitchenRepository kitchenRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKitchenDeleteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        kitchenRepository = KitchenRepository.getRepository(getApplication());


    binding.finalDeleteItemButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = KitchenActivity.kitchenActivityIntentFactory(getApplicationContext());
            String enteredFood = binding.enterDeletedItemBox.getText().toString();
            int enteredQuantity = 0;
            int foundQuantity = 0;
            Kitchen kitchenFound = null;

            for (Kitchen kitchens : kitchenRepository.getAllLogs()) { //update quantity amount
                if ( kitchens.getName().equals(enteredFood) && getLoggedInUserId() == kitchens.getUserId() ) {
                    foundQuantity = kitchens.getQuantity();
                    kitchenFound = kitchens;
                    break;
                }
            }

            try { //invalid entries
                enteredQuantity = Integer.parseInt(binding.enterQuantityDeletedBox.getText().toString());
            }   catch (NumberFormatException e) {
                Toast.makeText(KitchenDelete.this, "Quantity entered is not valid", Toast.LENGTH_SHORT).show();
            }
                //negative or 0
            if (enteredQuantity <= 0) {
                Toast.makeText(KitchenDelete.this, "Quantity cannot be less than or equal to 0", Toast.LENGTH_SHORT).show();
            } else if (kitchenFound == null) {
                Toast.makeText(KitchenDelete.this, "Food item not found", Toast.LENGTH_SHORT).show();
            }

            //not negative or less than 0
            else {

                //depending on amount, kitchen will be updated or deleted
                if ( enteredQuantity == foundQuantity ) { //delete entirely if entered quantity equals stored amount
                    kitchenRepository.deleteByFoodName(enteredFood);
                    Toast.makeText(KitchenDelete.this, "Food deleted!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);

                } else if ( enteredQuantity > foundQuantity ) {
                    Toast.makeText(KitchenDelete.this, "The number you entered is too big", Toast.LENGTH_SHORT).show();
                }
                else {
                    kitchenRepository.updateQuantity(enteredFood, foundQuantity - enteredQuantity); //update quantity
                    Toast.makeText(KitchenDelete.this, "Some of your food is now gone :(", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }
        }
    });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    static Intent kitchenDeleteActivitiyIntentFactory(Context context) {
        Intent intent = new Intent(context, KitchenDelete.class);
        return intent;
    }
}