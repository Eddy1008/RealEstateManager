package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.PropertyPhotoDAO;
import com.openclassrooms.realestatemanager.model.PropertyPhoto;

import java.util.List;

public class PropertyPhotoRepository {

    private final PropertyPhotoDAO propertyPhotoDAO;

    public PropertyPhotoRepository(PropertyPhotoDAO propertyPhotoDAO) {
        this.propertyPhotoDAO = propertyPhotoDAO;
    }

    // C
    public void createPropertyPhoto(PropertyPhoto propertyPhoto) {
        this.propertyPhotoDAO.createPropertyPhoto(propertyPhoto);
    }

    // R
    public LiveData<List<PropertyPhoto>> getPropertyPhotoList() {
        return this.propertyPhotoDAO.getPropertyPhotoList();
    }
    public LiveData<List<PropertyPhoto>> getPropertyPhotoByPropertyIdList(String propertyId) {
        return this.propertyPhotoDAO.getPropertyPhotoByPropertyIdList(propertyId);
    }

    // U
    public void updatePropertyPhoto(PropertyPhoto propertyPhoto) {
        this.propertyPhotoDAO.updatePropertyPhoto(propertyPhoto);
    }

    // D
    public void deletePropertyPhoto(String propertyPhotoId) {
        this.propertyPhotoDAO.deletePropertyPhoto(propertyPhotoId);
    }
}
