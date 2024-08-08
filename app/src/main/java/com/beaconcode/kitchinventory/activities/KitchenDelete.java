package com.beaconcode.kitchinventory.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.RecyclerView;

import com.beaconcode.kitchinventory.R;
import com.beaconcode.kitchinventory.data.database.KitchenRepository;
import com.beaconcode.kitchinventory.data.database.entities.Kitchen;
import com.beaconcode.kitchinventory.databinding.ActivityKitchenDeleteBinding;

import java.util.ArrayList;

public class KitchenDelete extends AppCompatActivity {

    private ArrayList<Kitchen> kitchenList = new ArrayList<>();
    private ArrayList<String> foodList = new ArrayList<>();
    private ActivityKitchenDeleteBinding binding;
    private KitchenRepository kitchenRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKitchenDeleteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    binding.finalDeleteItemButton.setOnClickListener(new View.OnClickListener()) {

        }

    }

    static Intent kitchenDeleteActivitiyIntentFactory(Context context) {
        Intent intent = new Intent(context, KitchenDelete.class);
        return intent;
    }
}