package com.openclassrooms.realestatemanager.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.model.PropertyPhoto;

import java.util.List;

@Dao
public interface PropertyPhotoDAO {
    // C
    @Insert
    void createPropertyPhoto(PropertyPhoto propertyPhoto);

    // R
    @Query("SELECT * FROM PropertyPhoto")
    LiveData<List<PropertyPhoto>> getPropertyPhotoList();
    @Query("SELECT * FROM PropertyPhoto WHERE propertyId = :propertyId")
    LiveData<List<PropertyPhoto>> getPropertyPhotoByPropertyIdList(String propertyId);

    // U
    @Update
    void updatePropertyPhoto(PropertyPhoto propertyPhoto);

    // D
    @Query("DELETE FROM PropertyType WHERE id = :propertyPhotoId")
    void deletePropertyPhoto(String propertyPhotoId);
}
