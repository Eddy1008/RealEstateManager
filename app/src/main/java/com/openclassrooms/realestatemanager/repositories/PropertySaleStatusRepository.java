package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.PropertySaleStatusDAO;
import com.openclassrooms.realestatemanager.model.PropertySaleStatus;

import java.util.List;

public class PropertySaleStatusRepository {

    private final PropertySaleStatusDAO propertySaleStatusDAO;

    public PropertySaleStatusRepository(PropertySaleStatusDAO propertySaleStatusDAO) {
        this.propertySaleStatusDAO = propertySaleStatusDAO;
    }

    // C
    public void createPropertySaleStatus(PropertySaleStatus propertySaleStatus) {
        this.propertySaleStatusDAO.createPropertySaleStatus(propertySaleStatus);
    }

    // R
    public LiveData<List<PropertySaleStatus>> getPropertySaleStatusList() {
        return this.propertySaleStatusDAO.getPropertySaleStatusList();
    }

    // U
    public void updatePropertySaleStatus(PropertySaleStatus propertySaleStatus) {
        this.propertySaleStatusDAO.updatePropertySaleStatus(propertySaleStatus);
    }

    // D
    public void deletePropertySaleStatus(String propertySaleStatusId) {
        this.propertySaleStatusDAO.deletePropertySaleStatus(propertySaleStatusId);
    }
}
