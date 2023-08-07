package com.openclassrooms.realestatemanager.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.model.PropertyType;

import java.util.List;

@Dao
public interface PropertyTypeDAO {
    // C
    @Insert
    void createPropertyType(PropertyType propertyType);

    // R
    @Query("SELECT * FROM PropertyType")
    LiveData<List<PropertyType>> getPropertyTypeList();

    // U
    @Update
    void updatePropertyType(PropertyType propertyType);

    // D
    @Query("DELETE FROM PropertyType WHERE id = :propertyTypeId")
    void deletePropertyType(String propertyTypeId);
}
