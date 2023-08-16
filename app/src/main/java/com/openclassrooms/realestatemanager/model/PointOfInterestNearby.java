package com.openclassrooms.realestatemanager.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Property.class, parentColumns = "id", childColumns = "propertyId"))
public class PointOfInterestNearby {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String address;
    private long propertyId;

    public PointOfInterestNearby(String name, String address, long propertyId) {
        this.name = name;
        this.address = address;
        this.propertyId = propertyId;
    }

    @Ignore
    public PointOfInterestNearby(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public long getPropertyId() {
        return propertyId;
    }
    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }

}
