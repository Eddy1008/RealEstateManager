package com.openclassrooms.realestatemanager.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.model.RealEstateAgent;

import java.util.List;

@Dao
public interface RealEstateAgentDAO {
    // C
    @Insert
    void createRealEstateAgent(RealEstateAgent realEstateAgent);

    // R
    @Query("SELECT * FROM RealEstateAgent")
    LiveData<List<RealEstateAgent>> getRealEstateAgentList();
    @Query("SELECT * FROM RealEstateAgent WHERE id = :realEstateAgentId")
    LiveData<RealEstateAgent> getRealEstateAgentById(String realEstateAgentId);

    // U
    @Update
    void updateRealEstateAgent(RealEstateAgent realEstateAgent);

    // D
    @Query("DELETE FROM RealEstateAgent WHERE id = :realEstateAgentId")
    void deleteRealEstateAgent(String realEstateAgentId);
}
