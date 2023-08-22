package com.openclassrooms.realestatemanager.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.model.PointOfInterestNearby;

import java.util.List;

@Dao
public interface PointOfInterestNearbyDAO {

    // C
    @Insert
    void createPointOfInterest(PointOfInterestNearby pointOfInterestNearby);

    // R
    @Query("SELECT * FROM PointOfInterestNearby")
    LiveData<List<PointOfInterestNearby>> getPointOfInterestList();
    @Query("SELECT * FROM PointOfInterestNearby WHERE propertyId = :propertyId")
    LiveData<List<PointOfInterestNearby>> getPointOfInterestByPropertyId(long propertyId);

    // U
    @Update
    void updatePointOfInterest(PointOfInterestNearby pointOfInterestNearby);

    // D
    @Query("DELETE FROM PointOfInterestNearby WHERE id = :pointOfInterestId")
    void deletePointOfInterest(long pointOfInterestId);
}
