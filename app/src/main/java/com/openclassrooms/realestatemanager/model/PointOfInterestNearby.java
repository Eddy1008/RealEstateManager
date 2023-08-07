package com.openclassrooms.realestatemanager.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Property.class, parentColumns = "id", childColumns = "propertyId"))
public class PointOfInterestNearby {

    @PrimaryKey
    private long id;
    private String name;
    private String linkUrl;
    private long propertyId;

    public PointOfInterestNearby(long id, String name, String linkUrl, long propertyId) {
        this.id = id;
        this.name = name;
        this.linkUrl = linkUrl;
        this.propertyId = propertyId;
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

    public String getLinkUrl() {
        return linkUrl;
    }
    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public long getPropertyId() {
        return propertyId;
    }
    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }

}
