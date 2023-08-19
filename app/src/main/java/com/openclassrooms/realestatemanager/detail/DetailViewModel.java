package com.openclassrooms.realestatemanager.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.model.PointOfInterestNearby;
import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.model.PropertyPhoto;
import com.openclassrooms.realestatemanager.repositories.PointOfInterestNearbyRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyPhotoRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;
import com.openclassrooms.realestatemanager.repositories.PropertySaleStatusRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyTypeRepository;
import com.openclassrooms.realestatemanager.repositories.RealEstateAgentRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class DetailViewModel extends ViewModel {

    // Repositories
    private final PointOfInterestNearbyRepository pointOfInterestNearbyRepository;
    private final PropertyPhotoRepository propertyPhotoRepository;
    private final PropertyRepository propertyRepository;
    private final PropertySaleStatusRepository propertySaleStatusRepository;
    private final PropertyTypeRepository propertyTypeRepository;
    private final RealEstateAgentRepository realEstateAgentRepository;
    private final Executor executor;

    // DATA
    private LiveData<List<PointOfInterestNearby>> pointOfInterestList;
    private LiveData<List<PropertyPhoto>> propertyPhotoList;

    public DetailViewModel(PointOfInterestNearbyRepository pointOfInterestNearbyRepository,
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

    // Data
    private final MutableLiveData<Property> myProperty = new MutableLiveData<>();
    public void setMyProperty(Property property) {
        myProperty.setValue(property);
    }
    public LiveData<Property> getMyProperty() {
        return this.myProperty;
    }

    // *********************************
    // ******* POINT OF INTEREST *******
    // *********************************

    public void initPointOfInterestListByPropertyId(long propertyId) {
        if (this.pointOfInterestList != null) {
            return;
        }
        pointOfInterestList = pointOfInterestNearbyRepository.getPointOfInterestNearbyByPropertyIdList(propertyId);
    }

    public LiveData<List<PointOfInterestNearby>> getPointOfInterestListByPropertyId() {
        return this.pointOfInterestList;
    }

    // ******************************
    // ******* PROPERTY PHOTO *******
    // ******************************

    public void initPropertyPhotoListByPropertyId(long propertyId) {
        if (this.propertyPhotoList != null) {
            return;
        }
        propertyPhotoList = propertyPhotoRepository.getPropertyPhotoByPropertyIdList(propertyId);
    }

    public LiveData<List<PropertyPhoto>> getPropertyPhotoListByPropertyId() {
        return this.propertyPhotoList;
    }

}
