package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.PointOfInterestNearbyDAO;
import com.openclassrooms.realestatemanager.model.PointOfInterestNearby;

import java.util.List;

public class PointOfInterestNearbyRepository {

    private final PointOfInterestNearbyDAO pointOfInterestNearbyDAO;

    public PointOfInterestNearbyRepository(PointOfInterestNearbyDAO pointOfInterestNearbyDAO) {
        this.pointOfInterestNearbyDAO = pointOfInterestNearbyDAO;
    }

    // C
    public void createPointOfInterest(PointOfInterestNearby pointOfInterestNearby) {
        this.pointOfInterestNearbyDAO.createPointOfInterest(pointOfInterestNearby);
    }

    // R
    public LiveData<List<PointOfInterestNearby>> getPointOfInterestNearbyList() {
        return this.pointOfInterestNearbyDAO.getPointOfInterestList();
    }
    public LiveData<List<PointOfInterestNearby>> getPointOfInterestNearbyByPropertyIdList(long propertyId) {
        return this.pointOfInterestNearbyDAO.getPointOfInterestByPropertyId(propertyId);
    }

    // U
    public void updatePointOfInterestNearby(PointOfInterestNearby pointOfInterestNearby) {
        this.pointOfInterestNearbyDAO.updatePointOfInterest(pointOfInterestNearby);
    }

    // D
    public void deletePointOfInterestNearby(long pointOfInterestId) {
        this.pointOfInterestNearbyDAO.deletePointOfInterest(pointOfInterestId);
    }
}
