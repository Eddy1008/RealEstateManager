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
    LiveData<Property> getPropertyById(long propertyId);

    @Query("SELECT * FROM Property WHERE " +
            "(:mySearchRequestTitle IS NULL OR title LIKE '%' || :mySearchRequestTitle || '%') AND " +
            "(:mySearchRequestAddress IS NULL OR address LIKE '%' || :mySearchRequestAddress || '%') AND " +
            "(:mySearchRequestSurfaceMin IS NULL OR propertySurface >= :mySearchRequestSurfaceMin) AND " +
            "(:mySearchRequestSurfaceMax IS NULL OR propertySurface <= :mySearchRequestSurfaceMax) AND " +
            "(:mySearchRequestRoom IS NULL OR roomNumber >= :mySearchRequestRoom) AND " +
            "(:mySearchRequestBathroom IS NULL OR bathroomNumber >= :mySearchRequestBathroom) AND " +
            "(:mySearchRequestBedroom IS NULL OR bedroomNumber >= :mySearchRequestBedroom) AND " +
            "(:mySearchRequestPrice IS NULL OR propertyPrice <= :mySearchRequestPrice) AND " +
            "(:mySearchRequestTypeId IS NULL OR propertyTypeId = :mySearchRequestTypeId) And " +
            "(:mySearchRequestAgentId IS NULL OR realEstateAgentId = :mySearchRequestAgentId)")
    LiveData<List<Property>> getPropertyBySpecifiedOptions2(
            String mySearchRequestTitle, String mySearchRequestAddress, String mySearchRequestSurfaceMin,
            String mySearchRequestSurfaceMax, String mySearchRequestRoom, String mySearchRequestBathroom,
            String mySearchRequestBedroom, String mySearchRequestPrice, String mySearchRequestTypeId, String mySearchRequestAgentId);

    // U
    @Update
    void updateProperty(Property property);

    // D
    @Query("DELETE FROM Property WHERE id = :propertyId")
    void deleteProperty(long propertyId);
}
