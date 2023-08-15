package com.openclassrooms.realestatemanager.add;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.model.PropertyType;
import com.openclassrooms.realestatemanager.model.RealEstateAgent;
import com.openclassrooms.realestatemanager.repositories.PointOfInterestNearbyRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyPhotoRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;
import com.openclassrooms.realestatemanager.repositories.PropertySaleStatusRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyTypeRepository;
import com.openclassrooms.realestatemanager.repositories.RealEstateAgentRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class AddPropertyViewModel extends ViewModel {

    // Repositories
    private final PointOfInterestNearbyRepository pointOfInterestNearbyRepository;
    private final PropertyPhotoRepository propertyPhotoRepository;
    private final PropertyRepository propertyRepository;
    private final PropertySaleStatusRepository propertySaleStatusRepository;
    private final PropertyTypeRepository propertyTypeRepository;
    private final RealEstateAgentRepository realEstateAgentRepository;
    private final Executor executor;

    // DATA
    private LiveData<List<PropertyType>> propertyTypeList;
    private LiveData<List<RealEstateAgent>> realEstateAgentList;
    private LiveData<List<Property>> propertyList;

    public AddPropertyViewModel(PointOfInterestNearbyRepository pointOfInterestNearbyRepository,
                                PropertyPhotoRepository propertyPhotoRepository,
                                PropertyRepository propertyRepository,
                                PropertySaleStatusRepository propertySaleStatusRepository,
                                PropertyTypeRepository propertyTypeRepository,
                                RealEstateAgentRepository realEstateAgentRepository,
                                Executor executor) {
        this.pointOfInterestNearbyRepository = pointOfInterestNearbyRepository;
        this.propertyPhotoRepository = propertyPhotoRepository;
        this.propertyRepository = propertyRepository;
        this.propertySaleStatusRepository = propertySaleStatusRepository;
        this.propertyTypeRepository = propertyTypeRepository;
        this.realEstateAgentRepository = realEstateAgentRepository;
        this.executor = executor;
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

    // ************************
    // ******* PROPERTY *******
    // ************************

    public void addProperty(Property property) {
        executor.execute(() -> propertyRepository.createProperty(property));
    }

}
