package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.PropertyDAO;
import com.openclassrooms.realestatemanager.model.Property;

import java.util.List;

public class PropertyRepository {

    private final PropertyDAO propertyDAO;

    public PropertyRepository(PropertyDAO propertyDAO) {
        this.propertyDAO = propertyDAO;
    }

    // C
    public void createProperty(Property property) {
        this.propertyDAO.createProperty(property);
    }

    // R
    public LiveData<List<Property>> getPropertyList() {
        return this.propertyDAO.getPropertyList();
    }
    public LiveData<Property> getPropertyById(String propertyId) {
        return this.propertyDAO.getPropertyById(propertyId);
    }

    // U
    public void updateProperty(Property property) {
        this.propertyDAO.updateProperty(property);
    }

    // D
    public void deleteProperty(String propertyId) {
        this.propertyDAO.deleteProperty(propertyId);
    }
}
