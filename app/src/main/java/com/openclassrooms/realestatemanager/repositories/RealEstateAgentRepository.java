package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.RealEstateAgentDAO;
import com.openclassrooms.realestatemanager.model.RealEstateAgent;

import java.util.List;

public class RealEstateAgentRepository {

    private final RealEstateAgentDAO realEstateAgentDAO;

    public RealEstateAgentRepository(RealEstateAgentDAO realEstateAgentDAO) {
        this.realEstateAgentDAO = realEstateAgentDAO;
    }

    // C
    public void createRealEstateAgent(RealEstateAgent realEstateAgent) {
        this.realEstateAgentDAO.createRealEstateAgent(realEstateAgent);
    }

    // R
    public LiveData<List<RealEstateAgent>> getRealEstateAgentList() {
        return this.realEstateAgentDAO.getRealEstateAgentList();
    }
    public LiveData<RealEstateAgent> getRealEstateAgentById(String realEstateAgentId) {
        return this.realEstateAgentDAO.getRealEstateAgentById(realEstateAgentId);
    }

    // U
    public void updateRealEstateAgent(RealEstateAgent realEstateAgent) {
        this.realEstateAgentDAO.updateRealEstateAgent(realEstateAgent);
    }

    // D
    public void deleteRealEstateAgent(String realEstateAgentId) {
        this.realEstateAgentDAO.deleteRealEstateAgent(realEstateAgentId);
    }
}
