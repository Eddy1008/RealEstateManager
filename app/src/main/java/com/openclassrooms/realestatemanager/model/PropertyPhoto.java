package com.openclassrooms.realestatemanager.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Property.class, parentColumns = "id", childColumns = "propertyId"))
public class PropertyPhoto {

    @PrimaryKey
    private long id;
    private String photoUrl;
    private String photoDescription;
    private long propertyId;

    public PropertyPhoto(long id, String photoUrl, String photoDescription, long propertyId) {
        this.id = id;
        this.photoUrl = photoUrl;
        this.photoDescription = photoDescription;
        this.propertyId = propertyId;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotoDescription() {
        return photoDescription;
    }
    public void setPhotoDescription(String photoDescription) {
        this.photoDescription = photoDescription;
    }

    public long getPropertyId() {
        return propertyId;
    }
    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }
}
