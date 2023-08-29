package com.openclassrooms.realestatemanager.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.model.PointOfInterestNearby;
import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.model.PropertyPhoto;
import com.openclassrooms.realestatemanager.model.PropertySaleStatus;
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

public class DetailViewModel extends ViewModel {

    // Repositories
    private final PointOfInterestNearbyRepository pointOfInterestNearbyRepository;
    private final PropertyPhotoRepository propertyPhotoRepository;
    private final PropertyRepository propertyRepository;
    private final PropertySaleStatusRepository propertySaleStatusRepository;
    private final PropertyTypeRepository propertyTypeRepository;
    private final RealEstateAgentRepository realEstateAgentRepository;

    // DATA
    private LiveData<List<PointOfInterestNearby>> pointOfInterestList;
    private LiveData<List<PropertyPhoto>> propertyPhotoList;
    private LiveData<List<PropertySaleStatus>> propertySaleStatusList;
    private LiveData<List<PropertyType>> propertyTypeList;
    private LiveData<List<RealEstateAgent>> realEstateAgentList;
    private final MutableLiveData<Property> myProperty = new MutableLiveData<>();

    public DetailViewModel(PointOfInterestNearbyRepository pointOfInterestNearbyRepository,
                           PropertyPhotoRepository propertyPhotoRepository,
                           PropertyRepository propertyRepository,
                           PropertySaleStatusRepository propertySaleStatusRepository,
                           PropertyTypeRepository propertyTypeRepository,
                           RealEstateAgentRepository realEstateAgentRepository) {
        this.pointOfInterestNearbyRepository = pointOfInterestNearbyRepository;
        this.propertyPhotoRepository = propertyPhotoRepository;
        this.propertyRepository = propertyRepository;
        this.propertySaleStatusRepository = propertySaleStatusRepository;
        this.propertyTypeRepository = propertyTypeRepository;
        this.realEstateAgentRepository = realEstateAgentRepository;
    }

    // ************************
    // ******* PROPERTY *******
    // ************************

    public void setMyProperty(Property property) {
        LiveData<Property> propertyFromDB = propertyRepository.getPropertyById(property.getId());
        propertyFromDB.observeForever(property1 -> {
            myProperty.setValue(property1);
        });
    }
    public LiveData<Property> getMyProperty() {
        return this.myProperty;
    }

    // *********************************
    // ******* POINT OF INTEREST *******
    // *********************************

    public void initPointOfInterestListByPropertyId(long propertyId) {
        pointOfInterestList = pointOfInterestNearbyRepository.getPointOfInterestNearbyByPropertyIdList(propertyId);
    }

    public LiveData<List<PointOfInterestNearby>> getPointOfInterestListByPropertyId() {
        return this.pointOfInterestList;
    }

    // ******************************
    // ******* PROPERTY PHOTO *******
    // ******************************

    public void initPropertyPhotoListByPropertyId(long propertyId) {
        propertyPhotoList = propertyPhotoRepository.getPropertyPhotoByPropertyIdList(propertyId);
    }

    public LiveData<List<PropertyPhoto>> getPropertyPhotoListByPropertyId() {
        return this.propertyPhotoList;
    }

    // ************************************
    // ******* PROPERTY SALE STATUS *******
    // ************************************

    public void initPropertySaleStatus() {
        if (this.propertySaleStatusList != null) {
            return;
        }
        propertySaleStatusList = propertySaleStatusRepository.getPropertySaleStatusList();
    }

    public LiveData<List<PropertySaleStatus>> getPropertySaleStatusList() {
        return this.propertySaleStatusList;
    }

    // *****************************
    // ******* PROPERTY TYPE *******
    // *****************************

    public void initPropertyType() {
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
