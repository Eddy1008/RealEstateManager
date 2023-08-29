package com.openclassrooms.realestatemanager.add;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.model.PointOfInterestNearby;
import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.model.PropertyPhoto;
import com.openclassrooms.realestatemanager.model.PropertyType;
import com.openclassrooms.realestatemanager.model.RealEstateAgent;
import com.openclassrooms.realestatemanager.repositories.PointOfInterestNearbyRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyPhotoRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;
import com.openclassrooms.realestatemanager.repositories.PropertySaleStatusRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyTypeRepository;
import com.openclassrooms.realestatemanager.repositories.RealEstateAgentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class AddPropertyViewModel extends ViewModel {

    // Repositories
    private final PointOfInterestNearbyRepository pointOfInterestNearbyRepository;
    private final PropertyPhotoRepository propertyPhotoRepository;
    private final PropertyRepository propertyRepository;
    private final PropertyTypeRepository propertyTypeRepository;
    private final RealEstateAgentRepository realEstateAgentRepository;
    private final Executor executor;

    // DATA
    private LiveData<List<PropertyType>> propertyTypeList;
    private LiveData<List<RealEstateAgent>> realEstateAgentList;
    private final MutableLiveData<List<PointOfInterestNearby>> pointOfInterestList = new MutableLiveData<>();
    private final List<PointOfInterestNearby> listOfPointOfInterestToAdd = new ArrayList<>();

    private final MutableLiveData<List<PropertyPhoto>> propertyPhotoList = new MutableLiveData<>();
    private final List<PropertyPhoto> listOfPropertyPhotoToAdd = new ArrayList<>();

    public AddPropertyViewModel(PointOfInterestNearbyRepository pointOfInterestNearbyRepository,
                                PropertyPhotoRepository propertyPhotoRepository,
                                PropertyRepository propertyRepository,
                                PropertyTypeRepository propertyTypeRepository,
                                RealEstateAgentRepository realEstateAgentRepository,
                                Executor executor) {
        this.pointOfInterestNearbyRepository = pointOfInterestNearbyRepository;
        this.propertyPhotoRepository = propertyPhotoRepository;
        this.propertyRepository = propertyRepository;
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

    public LiveData<Long> insertPropertyAndGetId(Property property) {
        return propertyRepository.createProperty(property);
    }


    // *********************************
    // ******* POINT OF INTEREST *******
    // *********************************

    public void deletePointOfInterestToAddList(PointOfInterestNearby pointOfInterestNearby) {
        this.listOfPointOfInterestToAdd.remove(pointOfInterestNearby);
        this.pointOfInterestList.setValue(listOfPointOfInterestToAdd);
    }

    public void addPointOfInterestToAddList(PointOfInterestNearby pointOfInterestNearby) {
        this.listOfPointOfInterestToAdd.add(pointOfInterestNearby);
        this.pointOfInterestList.setValue(listOfPointOfInterestToAdd);
    }

    public LiveData<List<PointOfInterestNearby>> getListOfPointOfInterestToAdd() {
        return this.pointOfInterestList;
    }

    public void addPointOfInterest(PointOfInterestNearby pointOfInterestNearby) {
        executor.execute(() -> pointOfInterestNearbyRepository.createPointOfInterest(pointOfInterestNearby));
    }

    // ******************************
    // ******* PROPERTY PHOTO *******
    // ******************************

    public void deletePropertyPhotoFromAddList(PropertyPhoto propertyPhoto) {
        this.listOfPropertyPhotoToAdd.remove(propertyPhoto);
        this.propertyPhotoList.setValue(listOfPropertyPhotoToAdd);
    }

    public void addPropertyPhotoToAddList(PropertyPhoto propertyPhoto) {
        this.listOfPropertyPhotoToAdd.add(propertyPhoto);
        this.propertyPhotoList.setValue(listOfPropertyPhotoToAdd);
    }

    public LiveData<List<PropertyPhoto>> getListOfPropertyPhotoToAdd() {
        return this.propertyPhotoList;
    }

    public void addPropertyPhoto(PropertyPhoto propertyPhoto) {
        executor.execute(() -> propertyPhotoRepository.createPropertyPhoto(propertyPhoto));
    }

}
