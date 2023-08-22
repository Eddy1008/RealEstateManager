package com.openclassrooms.realestatemanager.update;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
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

public class UpdatePropertyViewModel extends ViewModel {

    // Repositories
    private final PointOfInterestNearbyRepository pointOfInterestNearbyRepository;
    private final PropertyPhotoRepository propertyPhotoRepository;
    private final PropertyRepository propertyRepository;
    private final PropertySaleStatusRepository propertySaleStatusRepository;
    private final PropertyTypeRepository propertyTypeRepository;
    private final RealEstateAgentRepository realEstateAgentRepository;
    private final Executor executor;

    // DATA
    private final MutableLiveData<Property> propertyToUpdate = new MutableLiveData<>();
    private LiveData<List<PropertyType>> propertyTypeList;
    private LiveData<List<RealEstateAgent>> realEstateAgentList;

    private LiveData<List<PointOfInterestNearby>> pointOfInterestList;
    private final MediatorLiveData<List<PointOfInterestNearby>> mergedPointOfInterestListLiveData = new MediatorLiveData<>();

    private LiveData<List<PropertyPhoto>> propertyPhotoList;
    private final MediatorLiveData<List<PropertyPhoto>> mergedPropertyPhotoListLiveData = new MediatorLiveData<>();

    public UpdatePropertyViewModel(PointOfInterestNearbyRepository pointOfInterestNearbyRepository,
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

    public void setPropertyToUpdate(Property property) {
        propertyToUpdate.setValue(property);
    }

    public LiveData<Property> getMyPropertyToUpdate() {
        return this.propertyToUpdate;
    }

    public void updateProperty(Property property) {
        executor.execute(() -> propertyRepository.updateProperty(property));
    }

    // *********************************
    // ******* POINT OF INTEREST *******
    // *********************************

    public void initPointOfInterestList(long propertyId) {
        if (pointOfInterestList != null) {
            return;
        }
        pointOfInterestList = pointOfInterestNearbyRepository.getPointOfInterestNearbyByPropertyIdList(propertyId);

        mergedPointOfInterestListLiveData.addSource(pointOfInterestList, newData -> {
            mergedPointOfInterestListLiveData.setValue(newData);
        });
    }

    public LiveData<List<PointOfInterestNearby>> getPointOfInterestList() {
        return this.mergedPointOfInterestListLiveData;
    }

    public void addNewPointOfInterest(PointOfInterestNearby newPointOfInterest) {
        List<PointOfInterestNearby> currentList = mergedPointOfInterestListLiveData.getValue();

        if (currentList != null) {
            List<PointOfInterestNearby> newList = new ArrayList<>(currentList);
            newList.add(newPointOfInterest);
            addPointOfInterest(newPointOfInterest);
            mergedPointOfInterestListLiveData.setValue(newList);
        }
    }

    public void removePointOfInterest(PointOfInterestNearby pointOfInterestToRemove) {
        List<PointOfInterestNearby> currentList = mergedPointOfInterestListLiveData.getValue();

        if (currentList != null) {
            List<PointOfInterestNearby> newList = new ArrayList<>(currentList);
            newList.remove(pointOfInterestToRemove);
            deletePointOfInterest(pointOfInterestToRemove);
            mergedPointOfInterestListLiveData.setValue(newList);
        }
    }

    public void addPointOfInterest(PointOfInterestNearby pointOfInterestNearby) {
        executor.execute(() -> pointOfInterestNearbyRepository.createPointOfInterest(pointOfInterestNearby));
    }


    public void deletePointOfInterest(PointOfInterestNearby pointOfInterestNearby) {
        executor.execute(() -> pointOfInterestNearbyRepository.deletePointOfInterestNearby(pointOfInterestNearby.getId()));
    }

    // ******************************
    // ******* PROPERTY PHOTO *******
    // ******************************

    public void initPropertyPhotoList(long propertyId) {
        if (propertyPhotoList != null) {
            return;
        }
        propertyPhotoList = propertyPhotoRepository.getPropertyPhotoByPropertyIdList(propertyId);

        mergedPropertyPhotoListLiveData.addSource(propertyPhotoList, newData -> {
            mergedPropertyPhotoListLiveData.setValue(newData);
        });
    }

    public LiveData<List<PropertyPhoto>> getPropertyPhotoList() {
        return this.mergedPropertyPhotoListLiveData;
    }

    public void addNewPropertyPhoto(PropertyPhoto newPropertyPhoto) {
        List<PropertyPhoto> currentList = mergedPropertyPhotoListLiveData.getValue();

        if (currentList != null) {
            List<PropertyPhoto> newList= new ArrayList<>(currentList);
            newList.add(newPropertyPhoto);
            addPropertyPhoto(newPropertyPhoto);
            mergedPropertyPhotoListLiveData.setValue(newList);
        }
    }

    public void removePropertyPhoto(PropertyPhoto propertyPhotoToRemove) {
        List<PropertyPhoto> currentList = mergedPropertyPhotoListLiveData.getValue();

        if (currentList != null) {
            List<PropertyPhoto> newList = new ArrayList<>(currentList);
            newList.remove(propertyPhotoToRemove);
            deletePropertyPhoto(propertyPhotoToRemove);
            mergedPropertyPhotoListLiveData.setValue(newList);
        }
    }

    public void addPropertyPhoto(PropertyPhoto propertyPhoto) {
        executor.execute(() -> propertyPhotoRepository.createPropertyPhoto(propertyPhoto));
    }

    public void deletePropertyPhoto(PropertyPhoto propertyPhoto) {
        executor.execute(() -> propertyPhotoRepository.deletePropertyPhoto(propertyPhoto.getId()));
    }

}
