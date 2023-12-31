package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.realestatemanager.database.dao.PropertyDAO;
import com.openclassrooms.realestatemanager.model.Property;

import java.util.List;
import java.util.concurrent.Executors;

public class PropertyRepository {

    private final PropertyDAO propertyDAO;

    public PropertyRepository(PropertyDAO propertyDAO) {
        this.propertyDAO = propertyDAO;
    }

    // C
    public LiveData<Long> createProperty(Property property) {
        MutableLiveData<Long> insertedPropertyId = new MutableLiveData<>();
        Executors.newSingleThreadExecutor().execute(() -> {
            long id = propertyDAO.createProperty(property);
            insertedPropertyId.postValue(id);
        });
        return insertedPropertyId;
    }

    // R
    public LiveData<List<Property>> getPropertyList() {
        return this.propertyDAO.getPropertyList();
    }
    public LiveData<Property> getPropertyById(long propertyId) {
        return this.propertyDAO.getPropertyById(propertyId);
    }
    public LiveData<List<Property>> getPropertyBySpecifiedOptions(String mySearchRequestTitle, String mySearchRequestAddress, String mySearchRequestSurfaceMin,
                                                                  String mySearchRequestSurfaceMax, String mySearchRequestRoom, String mySearchRequestBathroom,
                                                                  String mySearchRequestBedroom, String mySearchRequestPrice, String mySearchRequestTypeId, String mySearchRequestAgentId) {
        return this.propertyDAO.getPropertyBySpecifiedOptions2(mySearchRequestTitle, mySearchRequestAddress, mySearchRequestSurfaceMin, mySearchRequestSurfaceMax, mySearchRequestRoom, mySearchRequestBathroom, mySearchRequestBedroom, mySearchRequestPrice, mySearchRequestTypeId, mySearchRequestAgentId);
    }

    // U
    public void updateProperty(Property property) {
        this.propertyDAO.updateProperty(property);
    }

    // D
    public void deleteProperty(long propertyId) {
        this.propertyDAO.deleteProperty(propertyId);
    }
}
