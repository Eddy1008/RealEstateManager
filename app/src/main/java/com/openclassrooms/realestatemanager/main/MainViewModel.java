package com.openclassrooms.realestatemanager.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.model.PropertyType;
import com.openclassrooms.realestatemanager.model.RealEstateAgent;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyTypeRepository;
import com.openclassrooms.realestatemanager.repositories.RealEstateAgentRepository;

import java.util.List;

public class MainViewModel extends ViewModel {

    // Repositories
    private final PropertyRepository propertyRepository;
    private final PropertyTypeRepository propertyTypeRepository;
    private final RealEstateAgentRepository realEstateAgentRepository;

    // Constructor
    public MainViewModel(PropertyRepository propertyRepository,
                         PropertyTypeRepository propertyTypeRepository,
                         RealEstateAgentRepository realEstateAgentRepository) {
        this.propertyRepository = propertyRepository;
        this.propertyTypeRepository = propertyTypeRepository;
        this.realEstateAgentRepository = realEstateAgentRepository;
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
    private LiveData<List<RealEstateAgent>> realEstateAgentList;
    private final MediatorLiveData<List<Property>> mergedPropertyListLiveData = new MediatorLiveData<>();


    // ************************
    // ******* PROPERTY *******
    // ************************
    public void initPropertyList() {
        if (this.propertyList != null) {
            return;
        }
        propertyList = propertyRepository.getPropertyList();

        mergedPropertyListLiveData.addSource(propertyList, newData -> {
            mergedPropertyListLiveData.setValue(newData);
        });
    }
    public LiveData<List<Property>> getPropertyList() {
        return this.mergedPropertyListLiveData;
    }

    public void getFilteredPropertyList(
            String mySearchRequestTitle, String mySearchRequestAddress, String mySearchRequestSurfaceMin,
            String mySearchRequestSurfaceMax, String mySearchRequestRoom, String mySearchRequestBathroom,
            String mySearchRequestBedroom, String mySearchRequestPrice, String mySearchRequestTypeId, String mySearchRequestAgentId) {

        propertyList = propertyRepository.getPropertyBySpecifiedOptions(mySearchRequestTitle, mySearchRequestAddress, mySearchRequestSurfaceMin, mySearchRequestSurfaceMax, mySearchRequestRoom, mySearchRequestBathroom, mySearchRequestBedroom, mySearchRequestPrice, mySearchRequestTypeId, mySearchRequestAgentId);
        mergedPropertyListLiveData.addSource(propertyList, newData -> {
                    mergedPropertyListLiveData.setValue(newData);
        });
    }


    // *****************************
    // ******* PROPERTY TYPE *******
    // *****************************
    public void initPropertyTypeList() {
        if (this.propertyTypeList != null) {
            return;
        }
        propertyTypeList = propertyTypeRepository.getPropertyTypeList();
    }
    public LiveData<List<PropertyType>> getPropertyTypeList() {
        return this.propertyTypeList;
    }

    // *********************************
    // ******* REAL ESTATE AGENT *******
    // *********************************

    public void initRealEstateAgentList() {
        if (this.realEstateAgentList != null) {
            return;
        }
        realEstateAgentList = realEstateAgentRepository.getRealEstateAgentList();
    }

    public LiveData<List<RealEstateAgent>> getRealEstateAgentList() {
        return this.realEstateAgentList;
    }
}
