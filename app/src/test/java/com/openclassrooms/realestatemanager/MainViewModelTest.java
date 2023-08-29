package com.openclassrooms.realestatemanager;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.openclassrooms.realestatemanager.main.MainViewModel;
import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.model.PropertyType;
import com.openclassrooms.realestatemanager.model.RealEstateAgent;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyTypeRepository;
import com.openclassrooms.realestatemanager.repositories.RealEstateAgentRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class MainViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private Observer<Boolean> observer;
    @Mock
    private Observer<List<Property>> observerListProperty;

    private MainViewModel mainViewModel;

    private PropertyRepository propertyRepository;
    private PropertyTypeRepository propertyTypeRepository;
    private RealEstateAgentRepository realEstateAgentRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Create a mock MutableLiveData<List<Property>>
        MutableLiveData<List<Property>> mockLiveDataPropertyList = new MutableLiveData<>();
        // Set the value you want to simulate (here empty list)
        List<Property> mockPropertyList = new ArrayList<>();
        mockLiveDataPropertyList.setValue(mockPropertyList);

        // Create a mock MutableLiveData<List<PropertyType>>
        MutableLiveData<List<PropertyType>> mockLiveDataPropertyTypeList = new MutableLiveData<>();
        // Set the value you want to simulate (here empty list)
        List<PropertyType> mockPropertyTypeList = new ArrayList<>();
        mockLiveDataPropertyTypeList.setValue(mockPropertyTypeList);

        // Create a mock MutableLiveData<List<PropertyType>>
        MutableLiveData<List<RealEstateAgent>> mockLiveDataRealEstateAgentList = new MutableLiveData<>();
        // Set the value you want to simulate (here empty list)
        List<RealEstateAgent> mockRealEstateAgentList = new ArrayList<>();
        mockLiveDataRealEstateAgentList.setValue(mockRealEstateAgentList);

        propertyRepository = mock(PropertyRepository.class);
        when(propertyRepository.getPropertyList()).thenReturn(mockLiveDataPropertyList);

        propertyTypeRepository = mock(PropertyTypeRepository.class);
        when(propertyTypeRepository.getPropertyTypeList()).thenReturn(mockLiveDataPropertyTypeList);

        realEstateAgentRepository = mock(RealEstateAgentRepository.class);
        when(realEstateAgentRepository.getRealEstateAgentList()).thenReturn(mockLiveDataRealEstateAgentList);

        mainViewModel = new MainViewModel(propertyRepository, propertyTypeRepository, realEstateAgentRepository);
    }

    @Test
    public void testGetIsTwoPaneMode() {
        mainViewModel.getIsTwoPaneMode().observeForever(observer);
        mainViewModel.setIsTwoPaneMode(true);
        verify(observer).onChanged(true);
    }

    // ************************
    // ******* PROPERTY *******
    // ************************
    @Test
    public void testInitPropertyList() {
        mainViewModel.initPropertyList();
        verify(propertyRepository).getPropertyList();
    }

    @Test
    public void testGetPropertyList() {
        LiveData<List<Property>> propertyListLiveData = mainViewModel.getPropertyList();
        assertSame(mainViewModel.getPropertyList(), propertyListLiveData);
    }

    @Test
    public void testGetFilteredPropertyList() {
        // Prepare your mock LiveData for the observer
        MutableLiveData<List<Property>> mockLiveDataPropertyFilteredList = new MutableLiveData<>();
        List<Property> mockPropertyFilteredList = new ArrayList<>();
        mockLiveDataPropertyFilteredList.setValue(mockPropertyFilteredList);

        // Set up observer
        mainViewModel.getPropertyList().observeForever(observerListProperty);

        // Mock the behavior of getPropertyBySpecifiedOptions() to return your mock filtered list
        when(propertyRepository.getPropertyBySpecifiedOptions(
                anyString(), anyString(), anyString(),
                anyString(), anyString(), anyString(),
                anyString(), anyString(), anyString(), anyString()
        )).thenReturn(mockLiveDataPropertyFilteredList);

        // Call the method you want to test
        mainViewModel.getFilteredPropertyList(
                "searchTitle", "searchAddress", "searchSurfaceMin",
                "searchSurfaceMax", "searchRoom", "searchBathroom",
                "searchBedroom", "searchPrice", "searchTypeId", "searchAgentId");

        // Verify that the PropertyRepository's getPropertyBySpecifiedOptions() method was called
        verify(propertyRepository).getPropertyBySpecifiedOptions(
                "searchTitle", "searchAddress", "searchSurfaceMin",
                "searchSurfaceMax", "searchRoom", "searchBathroom",
                "searchBedroom", "searchPrice", "searchTypeId", "searchAgentId"
        );

        // Verify that the observer is notified with the expected value
        verify(observerListProperty).onChanged(mockPropertyFilteredList);
    }

    // *****************************
    // ******* PROPERTY TYPE *******
    // *****************************
    @Test
    public void testInitPropertyTypeList() {
        mainViewModel.initPropertyTypeList();
        verify(propertyTypeRepository).getPropertyTypeList();
    }

    @Test
    public void testGetPropertyTypeList() {
        LiveData<List<PropertyType>> propertyTypeListLiveData = mainViewModel.getPropertyTypeList();
        assertSame(mainViewModel.getPropertyTypeList(), propertyTypeListLiveData);
    }

    // *********************************
    // ******* REAL ESTATE AGENT *******
    // *********************************
    @Test
    public void testInitRealEstateAgentList() {
        mainViewModel.initRealEstateAgentList();
        verify(realEstateAgentRepository).getRealEstateAgentList();
    }

    @Test
    public void testGetRealEstateAgentList() {
        LiveData<List<RealEstateAgent>> realEstateAgentListLiveData = mainViewModel.getRealEstateAgentList();
        assertSame(mainViewModel.getRealEstateAgentList(), realEstateAgentListLiveData);
    }
}
