package com.openclassrooms.realestatemanager.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.model.Property;

import java.util.List;

@Dao
public interface PropertyDAO {
    // C
    @Insert
    long createProperty(Property property);

    // R
    @Query("SELECT * FROM Property")
    LiveData<List<Property>> getPropertyList();
    @Query("SELECT * FROM Property WHERE id = :propertyId")
    LiveData<Property> getPropertyById(String propertyId);

    // U
    @Update
    void updateProperty(Property property);

    // D
    @Query("DELETE FROM Property WHERE id = :propertyId")
    void deleteProperty(String propertyId);
}
