package com.beaconcode.kitchinventory.ui.viewmodels;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.beaconcode.kitchinventory.data.database.KitchenRepository;

import java.util.List;

/**
 * ViewModel class for managing UI-related data in a lifecycle-conscious way.
 * This ViewModel interacts with the KitchenRepository to fetch the list of food items.
 */
public class CookViewModel extends ViewModel {
    private final KitchenRepository repository;

    /**
     * Constructor for CookViewModel.
     * Initializes the repository instance.
     *
     * @param application The application context used to initialize the repository.
     */
    public CookViewModel(Application application) {
        this.repository = KitchenRepository.getRepository(application);
    }

    /**
     * Returns a LiveData object containing the list of food items.
     * The LiveData object allows the UI to observe changes to the data.
     *
     * @return LiveData object containing a list of food item names.
     */
    public LiveData<List<String>> getFoodList() {
        return repository.getFoodList();
    }
}
