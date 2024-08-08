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
            String enteredFood = binding.enterDeletedItemBox.getText().toString();
            int enteredQuantity = 0;
            Integer foundQuantity = 0;
            Kitchen kitchenFound = new Kitchen();

            try { //invalid entries
                enteredQuantity = Integer.parseInt(binding.enterQuantityDeletedBox.getText().toString());
            }   catch (NumberFormatException e) {
                Toast.makeText(KitchenDelete.this, "Quantity entered is not valid", Toast.LENGTH_SHORT).show();
            }
                //negative or 0
            if (enteredQuantity <= 0) {
                Toast.makeText(KitchenDelete.this, "Quantity cannot be less than or equal to 0", Toast.LENGTH_SHORT).show();
            }

            //not negative or less than 0
            else {

                for (Kitchen kitchens : kitchenRepository.getAllLogs()) { //update quantity amount
                    if ( kitchens.getName().equals(enteredFood) && getLoggedInUserId() == kitchens.getUserId() ) {
                        foundQuantity = kitchens.getQuantity();
                        kitchenFound = kitchens;
                        break;
                    }
                }

                //depending on amount, kitchen will be updated or deleted
                if ( enteredQuantity == foundQuantity ) { //delete entirely if entered quantity equals stored amount
                    kitchenRepository.deleteByFoodName(enteredFood);
                } else if ( enteredQuantity > foundQuantity ) {
                    Toast.makeText(KitchenDelete.this, "The number you entered is too big", Toast.LENGTH_SHORT).show();
                }
                else {
                    kitchenFound.setQuantity(foundQuantity - enteredQuantity); //update quantity
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