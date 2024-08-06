package com.beaconcode.kitchinventory.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beaconcode.kitchinventory.R;
import com.beaconcode.kitchinventory.data.database.KitchenRepository;
import com.beaconcode.kitchinventory.data.database.ShoppingListRepository;
import com.beaconcode.kitchinventory.data.database.entities.Kitchen;
import com.beaconcode.kitchinventory.data.database.entities.ShoppingList;
import com.beaconcode.kitchinventory.databinding.ActivityShoppingListBinding;
import com.beaconcode.kitchinventory.ui.adapters.ShoppingListAdapter;
import com.beaconcode.kitchinventory.ui.view.CookInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity will display the current items in the user's shopping list from the database.
 */
public class ShoppingListActivity extends AppCompatActivity implements CookInterface {

    private ArrayList<String> shoppingList = new ArrayList<>();
    private ActivityShoppingListBinding binding;
    private KitchenRepository shoppingListRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShoppingListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RecyclerView recyclerView = binding.shoppingListRecyclerView;

        shoppingListRepository = KitchenRepository.getRepository(getApplication());

        setUpFoodList();
        getFoodList();

        ShoppingListAdapter adapter = new ShoppingListAdapter(this, shoppingList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //setContentView(R.layout.activity_shopping_list);
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
        shoppingList.add("Pork");
        shoppingList.add("Salmon");
        shoppingList.add("Tofu");
        shoppingList.add("Banana");
        shoppingList.add("Milk");
        shoppingList.add("Lentils");
        shoppingList.add("Rice");
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
    public void onItemClick(int position) {
       // Intent intent = RecipesActivity.recipesActivityIntentFactory(getApplicationContext(), shoppingList.get(position));
       // startActivity(intent);
        String text = shoppingList.get(position);
        //Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

}
}