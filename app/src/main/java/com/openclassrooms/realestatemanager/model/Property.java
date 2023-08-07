package com.openclassrooms.realestatemanager.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = PropertyType.class, parentColumns = "id", childColumns = "propertyTypeId"),
        @ForeignKey(entity = PropertySaleStatus.class, parentColumns = "id", childColumns = "propertySaleStatusId"),
        @ForeignKey(entity = RealEstateAgent.class, parentColumns = "id", childColumns = "realEstateAgentId")
})
public class Property {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private String address;
    private String propertyDescription;
    private String onSaleDate;
    private String saleDealDate;
    private int propertyPrice;
    private int propertySurface;
    private int roomNumber;
    private int bathroomNumber;
    private int bedroomNumber;
    private long propertyTypeId;
    private long propertySaleStatusId;
    private long realEstateAgentId;

    public Property(String title, String address, String propertyDescription,
                    String onSaleDate, String saleDealDate, int propertyPrice,
                    int propertySurface, int roomNumber, int bathroomNumber, int bedroomNumber,
                    long propertyTypeId, long propertySaleStatusId, long realEstateAgentId) {
        this.title = title;
        this.address = address;
        this.propertyDescription = propertyDescription;
        this.onSaleDate = onSaleDate;
        this.saleDealDate = saleDealDate;
        this.propertyPrice = propertyPrice;
        this.propertySurface = propertySurface;
        this.roomNumber = roomNumber;
        this.bathroomNumber = bathroomNumber;
        this.bedroomNumber = bedroomNumber;
        this.propertyTypeId = propertyTypeId;
        this.propertySaleStatusId = propertySaleStatusId;
        this.realEstateAgentId = realEstateAgentId;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getPropertyDescription() {
        return propertyDescription;
    }
    public void setPropertyDescription(String propertyDescription) {
        this.propertyDescription = propertyDescription;
    }

    public String getOnSaleDate() {
        return onSaleDate;
    }
    public void setOnSaleDate(String onSaleDate) {
        this.onSaleDate = onSaleDate;
    }

    public String getSaleDealDate() {
        return saleDealDate;
    }
    public void setSaleDealDate(String saleDealDate) {
        this.saleDealDate = saleDealDate;
    }

    public int getPropertyPrice() {
        return propertyPrice;
    }
    public void setPropertyPrice(int propertyPrice) {
        this.propertyPrice = propertyPrice;
    }

    public int getPropertySurface() {
        return propertySurface;
    }
    public void setPropertySurface(int propertySurface) {
        this.propertySurface = propertySurface;
    }

    public int getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getBathroomNumber() {
        return bathroomNumber;
    }
    public void setBathroomNumber(int bathroomNumber) {
        this.bathroomNumber = bathroomNumber;
    }

    public int getBedroomNumber() {
        return bedroomNumber;
    }
    public void setBedroomNumber(int bedroomNumber) {
        this.bedroomNumber = bedroomNumber;
    }

    public long getPropertyTypeId() {
        return propertyTypeId;
    }
    public void setPropertyTypeId(long propertyTypeId) {
        this.propertyTypeId = propertyTypeId;
    }

    public long getPropertySaleStatusId() {
        return propertySaleStatusId;
    }
    public void setPropertySaleStatusId(long propertySaleStatusId) {
        this.propertySaleStatusId = propertySaleStatusId;
    }

    public long getRealEstateAgentId() {
        return realEstateAgentId;
    }
    public void setRealEstateAgentId(long realEstateAgentId) {
        this.realEstateAgentId = realEstateAgentId;
    }
}
