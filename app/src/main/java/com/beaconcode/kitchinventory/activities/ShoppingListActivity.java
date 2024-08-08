package com.beaconcode.kitchinventory.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beaconcode.kitchinventory.R;
import com.beaconcode.kitchinventory.data.database.ShoppingListRepository;
import com.beaconcode.kitchinventory.data.database.entities.ShoppingList;
import com.beaconcode.kitchinventory.databinding.ActivityShoppingListBinding;
import com.beaconcode.kitchinventory.ui.adapters.ShoppingListAdapter;
import com.beaconcode.kitchinventory.ui.view.CookInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity will display the current items in the user's shopping list from the database.
 */
public class ShoppingListActivity extends BaseActivity implements CookInterface {

    private ArrayList<String> shoppingList = new ArrayList<>();
    private ActivityShoppingListBinding binding;
    private ShoppingListRepository shoppingListRepository;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShoppingListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.shoppingListRecyclerView;

        shoppingListRepository = ShoppingListRepository.getRepository(getApplication());

        recyclerViewSetup();

        binding.addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.itemName.getText().toString();
                int quantity = 0;
                try {
                    quantity = Integer.parseInt(binding.itemQuantity.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(ShoppingListActivity.this, "Plz enter a valid number", Toast.LENGTH_SHORT).show();;
                }

                if (quantity > 0)
                {
                    //UPDATED THIS LINE TO INCLUDE NEW USERID PARAMETER
                    ShoppingList item = new ShoppingList(name, quantity, getLoggedInUserId());
                    shoppingListRepository.insertShoppingList(item);

                }

                //doesn't always work :/
                shoppingList.clear(); // IDK why but these lines need to be called twice
                recyclerViewSetup();  // Otherwise the recyclerview will print the items
                shoppingList.clear();  // That were stored previously, as well as the items
                recyclerViewSetup();  // That are being added when button is clicked
                binding.itemName.getText().clear();
                binding.itemQuantity.getText().clear();

            }
        });

        binding.deleteItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.itemName.getText().toString();

                shoppingListRepository.deleteByFoodName(name);

                // setUpFoodList();
                shoppingList.clear();
                recyclerViewSetup();
                shoppingList.clear();
                recyclerViewSetup();

            }
        });
    }

    private void recyclerViewSetup(){
        setUpFoodList();
        getFoodList();
        ShoppingListAdapter adapter = new ShoppingListAdapter(ShoppingListActivity.this, shoppingList, ShoppingListActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(null));
    }

    private void getFoodList() {
        LiveData<List<String>> userObserver = shoppingListRepository.getFoodList();
        userObserver.observe(this, list -> {
            this.shoppingList = (ArrayList<String>) list;
            if (list != null) {
                invalidateOptionsMenu();
            }
        });
    }

    /**
     * Sets up the list of food items to be displayed in the RecyclerView.
     * Adds predefined food items to the foodList.
     * This method should be replaced with fetching data from a room database when possible.
     */
    private void setUpFoodList() {
        try {
            for (ShoppingList food : shoppingListRepository.getAllShoppingList()) {
                shoppingList.add(food.getName());
            }
        } catch (Exception e) {
            Toast.makeText(this, "Unknown error", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Initialize the contents of the Activity's standard options menu.
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed; if you return false it will not be shown.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    /**
     * Factory method to create an Intent for ShoppingListActivity.
     * @param context The context from which the activity is started.
     * @return An Intent to start ShoppingListActivity.
     */
    static Intent shoppingListActivityIntentFactory(Context context) {
        return new Intent(context, ShoppingListActivity.class);
    }

    @Override
    public void onItemClick(String foodname) {
        // Intent intent = RecipesActivity.recipesActivityIntentFactory(getApplicationContext(), shoppingList.get(position));
        // startActivity(intent);
        String text = foodname;
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

    }
}