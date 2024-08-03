package com.beaconcode.kitchinventory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beaconcode.kitchinventory.databinding.ActivityCookBinding;
import com.beaconcode.kitchinventory.views.CookAdapter;
import com.beaconcode.kitchinventory.views.CookInterface;

import java.util.ArrayList;

public class CookActivity extends AppCompatActivity implements CookInterface {

    private String foodName;
    private ArrayList<String> foodList = new ArrayList<>();
    private ActivityCookBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        RecyclerView recyclerView = binding.rvCook;

        setUpFoodList();
        CookAdapter adapter = new CookAdapter(this, foodList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        };

    private void setUpFoodList() {
        foodList.add("Chicken");
        foodList.add("Beef");
        foodList.add("Pork");
        foodList.add("Salmon");
        foodList.add("Tofu");
        foodList.add("Bananas");
        foodList.add("Milk");
        foodList.add("Tortillas");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    static Intent cookActivityIntentFactory(Context context) {
        return new Intent(context, CookActivity.class);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = RecipesActivity.recipesActivityIntentFactory(getApplicationContext(), foodList.get(position));
        startActivity(intent);
    }
}