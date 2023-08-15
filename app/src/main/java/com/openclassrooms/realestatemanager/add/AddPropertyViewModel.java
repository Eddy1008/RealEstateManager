package com.openclassrooms.realestatemanager.add;

import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.repositories.PointOfInterestNearbyRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyPhotoRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;
import com.openclassrooms.realestatemanager.repositories.PropertySaleStatusRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyTypeRepository;
import com.openclassrooms.realestatemanager.repositories.RealEstateAgentRepository;

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
    
}