package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.PropertyTypeDAO;
import com.openclassrooms.realestatemanager.model.PropertyType;

import java.util.List;

public class PropertyTypeRepository {

    private final PropertyTypeDAO propertyTypeDAO;

    public PropertyTypeRepository(PropertyTypeDAO propertyTypeDAO) {
        this.propertyTypeDAO = propertyTypeDAO;
    }

    // C
    public void createPropertyType(PropertyType propertyType) {
        this.propertyTypeDAO.createPropertyType(propertyType);
    }

    // R
    public LiveData<List<PropertyType>> getPropertyTypeList() {
        return this.propertyTypeDAO.getPropertyTypeList();
    }

    // U
    public void updatePropertyType(PropertyType propertyType) {
        this.propertyTypeDAO.updatePropertyType(propertyType);
    }

    // D
    public void deletePropertyType(String propertyTypeId) {
        this.propertyTypeDAO.deletePropertyType(propertyTypeId);
    }
}
