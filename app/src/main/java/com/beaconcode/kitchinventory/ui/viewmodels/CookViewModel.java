package com.beaconcode.kitchinventory.ui.viewmodels;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.beaconcode.kitchinventory.data.database.KitchenRepository;

import java.util.List;

public class CookViewModel extends ViewModel {
    private final KitchenRepository repository;

    public CookViewModel(Application application) {
        this.repository = KitchenRepository.getRepository(application);
    }
    public LiveData<List<String>> getFoodList() {
        return repository.getFoodList();
    }
}
