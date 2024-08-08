package com.beaconcode.kitchinventory.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;



import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.beaconcode.kitchinventory.R;
import com.beaconcode.kitchinventory.databinding.ActivityKitchenAddBinding;

public class KitchenAdd extends BaseActivity {

    private ActivityKitchenAddBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKitchenAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    static Intent kitchenAddActivityIntentFactory(Context context) {
        return new Intent(context, KitchenAdd.class);
    }
}