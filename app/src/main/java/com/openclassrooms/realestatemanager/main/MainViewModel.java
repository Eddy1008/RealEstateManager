package com.openclassrooms.realestatemanager.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.model.PropertyType;
import com.openclassrooms.realestatemanager.repositories.PointOfInterestNearbyRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyPhotoRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;
import com.openclassrooms.realestatemanager.repositories.PropertySaleStatusRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyTypeRepository;
import com.openclassrooms.realestatemanager.repositories.RealEstateAgentRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class MainViewModel extends ViewModel {

    // Repositories
    private final PropertyRepository propertyRepository;
    private final PropertyTypeRepository propertyTypeRepository;
    private final Executor executor;

    // Constructor
    public MainViewModel(PropertyRepository propertyRepository,
                         PropertyTypeRepository propertyTypeRepository,
                         Executor executor) {
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
    private LiveData<List<Property>> propertyList;
    private LiveData<List<PropertyType>> propertyTypeList;

    // Methods:
    public void initPropertyList() {
        if (this.propertyList != null) {
            return;
        }
        propertyList = propertyRepository.getPropertyList();
    }

    public void initPropertyTypeList() {
        if (this.propertyTypeList != null) {
            return;
        }
        propertyTypeList = propertyTypeRepository.getPropertyTypeList();
    }

    public LiveData<List<Property>> getPropertyList() {
        return this.propertyList;
    }

    public LiveData<List<PropertyType>> getPropertyTypeList() {
        return this.propertyTypeList;
    }
}
