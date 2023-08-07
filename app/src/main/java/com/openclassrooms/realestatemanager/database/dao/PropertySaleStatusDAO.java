package com.openclassrooms.realestatemanager.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.model.PropertySaleStatus;

import java.util.List;

@Dao
public interface PropertySaleStatusDAO {
    // C
    @Insert
    void createPropertySaleStatus(PropertySaleStatus propertySaleStatus);

    // R
    @Query("SELECT * FROM PropertySaleStatus")
    LiveData<List<PropertySaleStatus>> getPropertySaleStatusList();

    // U
    @Update
    void updatePropertySaleStatus(PropertySaleStatus propertySaleStatus);

    // D
    @Query("DELETE FROM PropertySaleStatus WHERE id = :propertySaleStatusId")
    void deletePropertySaleStatus(String propertySaleStatusId);
}
