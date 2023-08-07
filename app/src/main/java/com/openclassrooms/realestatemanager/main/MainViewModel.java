package com.openclassrooms.realestatemanager.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.repositories.PointOfInterestNearbyRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyPhotoRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;
import com.openclassrooms.realestatemanager.repositories.PropertySaleStatusRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyTypeRepository;
import com.openclassrooms.realestatemanager.repositories.RealEstateAgentRepository;

import java.util.concurrent.Executor;

public class MainViewModel extends ViewModel {

    // Repositories
    private final PropertyPhotoRepository propertyPhotoRepository;
    private final PropertyRepository propertyRepository;
    private final PropertyTypeRepository propertyTypeRepository;
    private final Executor executor;

    public MainViewModel(PropertyPhotoRepository propertyPhotoRepository,
                         PropertyRepository propertyRepository,
                         PropertyTypeRepository propertyTypeRepository,
                         Executor executor) {
        this.propertyPhotoRepository = propertyPhotoRepository;
        this.propertyRepository = propertyRepository;
        this.propertyTypeRepository = propertyTypeRepository;
        this.executor = executor;
    }

    // Data
    private final MutableLiveData<Boolean> isTwoPaneMode = new MutableLiveData<>();
    public void setIsTwoPaneMode(boolean isTwoPane) {
        isTwoPaneMode.setValue(isTwoPane);
    }
    public LiveData<Boolean> getIsTwoPaneMode() {
        return isTwoPaneMode;
    }
}
