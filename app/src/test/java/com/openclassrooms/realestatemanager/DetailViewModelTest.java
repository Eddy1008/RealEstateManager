package com.openclassrooms.realestatemanager;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.realestatemanager.detail.DetailViewModel;
import com.openclassrooms.realestatemanager.model.PointOfInterestNearby;
import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.model.PropertyPhoto;
import com.openclassrooms.realestatemanager.model.PropertySaleStatus;
import com.openclassrooms.realestatemanager.model.PropertyType;
import com.openclassrooms.realestatemanager.model.RealEstateAgent;
import com.openclassrooms.realestatemanager.repositories.PointOfInterestNearbyRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyPhotoRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;
import com.openclassrooms.realestatemanager.repositories.PropertySaleStatusRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyTypeRepository;
import com.openclassrooms.realestatemanager.repositories.RealEstateAgentRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class DetailViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private DetailViewModel detailViewModel;

    private Property propertyById;

    private PointOfInterestNearbyRepository pointOfInterestNearbyRepository;
    private PropertyPhotoRepository propertyPhotoRepository;
    private PropertyRepository propertyRepository;
    private PropertySaleStatusRepository propertySaleStatusRepository;
    private PropertyTypeRepository propertyTypeRepository;
    private RealEstateAgentRepository realEstateAgentRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        MutableLiveData<Property> mockLiveDataPropertyById = new MutableLiveData<>();
        propertyById = new Property(
                "test", "test", "test", "test",
                "test", "test", 100000 , 75,
                6, 1, 3,
                1, 1, 1);
        mockLiveDataPropertyById.setValue(propertyById);

        propertyRepository = mock(PropertyRepository.class);
        when(propertyRepository.getPropertyById(propertyById.getId())).thenReturn(mockLiveDataPropertyById);

        MutableLiveData<List<PointOfInterestNearby>> mockLiveDataPointOfInterestNearbyList = new MutableLiveData<>();
        List<PointOfInterestNearby> mockPointOfInterestNearbyList = new ArrayList<>();
        mockLiveDataPointOfInterestNearbyList.setValue(mockPointOfInterestNearbyList);

        pointOfInterestNearbyRepository = mock(PointOfInterestNearbyRepository.class);
        when(pointOfInterestNearbyRepository.getPointOfInterestNearbyByPropertyIdList(propertyById.getId())).thenReturn(mockLiveDataPointOfInterestNearbyList);

        MutableLiveData<List<PropertyPhoto>> mockLiveDataPropertyPhotoList = new MutableLiveData<>();
        List<PropertyPhoto> mockPropertyPhotoList = new ArrayList<>();
        mockLiveDataPropertyPhotoList.setValue(mockPropertyPhotoList);

        propertyPhotoRepository = mock(PropertyPhotoRepository.class);
        when(propertyPhotoRepository.getPropertyPhotoByPropertyIdList(propertyById.getId())).thenReturn(mockLiveDataPropertyPhotoList);

        // Create a mock MutableLiveData<List<PropertySaleStatus>>
        MutableLiveData<List<PropertySaleStatus>> mockLiveDataPropertySaleStatusList = new MutableLiveData<>();
        // Set the value you want to simulate (here empty list)
        List<PropertySaleStatus> mockPropertySaleStatusList = new ArrayList<>();
        mockLiveDataPropertySaleStatusList.setValue(mockPropertySaleStatusList);

        propertySaleStatusRepository = mock(PropertySaleStatusRepository.class);
        when(propertySaleStatusRepository.getPropertySaleStatusList()).thenReturn(mockLiveDataPropertySaleStatusList);

        // Create a mock MutableLiveData<List<PropertyType>>
        MutableLiveData<List<PropertyType>> mockLiveDataPropertyTypeList = new MutableLiveData<>();
        // Set the value you want to simulate (here empty list)
        List<PropertyType> mockPropertyTypeList = new ArrayList<>();
        mockLiveDataPropertyTypeList.setValue(mockPropertyTypeList);

        propertyTypeRepository = mock(PropertyTypeRepository.class);
        when(propertyTypeRepository.getPropertyTypeList()).thenReturn(mockLiveDataPropertyTypeList);

        // Create a mock MutableLiveData<List<PropertyType>>
        MutableLiveData<List<RealEstateAgent>> mockLiveDataRealEstateAgentList = new MutableLiveData<>();
        // Set the value you want to simulate (here empty list)
        List<RealEstateAgent> mockRealEstateAgentList = new ArrayList<>();
        mockLiveDataRealEstateAgentList.setValue(mockRealEstateAgentList);

        realEstateAgentRepository = mock(RealEstateAgentRepository.class);
        when(realEstateAgentRepository.getRealEstateAgentList()).thenReturn(mockLiveDataRealEstateAgentList);

        detailViewModel = new DetailViewModel(
                pointOfInterestNearbyRepository, propertyPhotoRepository, propertyRepository,
                propertySaleStatusRepository, propertyTypeRepository, realEstateAgentRepository);
    }

    // ************************
    // ******* PROPERTY *******
    // ************************
    @Test
    public void testSetMyProperty() {
        detailViewModel.setMyProperty(propertyById);
        verify(propertyRepository).getPropertyById(propertyById.getId());
    }

    @Test
    public void testGetMyProperty() {
        LiveData<Property> propertyToGet = detailViewModel.getMyProperty();
        assertSame(detailViewModel.getMyProperty(), propertyToGet);
    }

    // *********************************
    // ******* POINT OF INTEREST *******
    // *********************************
    @Test
    public void testInitPointOfInterestListByPropertyId() {
        detailViewModel.initPointOfInterestListByPropertyId(propertyById.getId());
        verify(pointOfInterestNearbyRepository).getPointOfInterestNearbyByPropertyIdList(propertyById.getId());
    }

    @Test
    public void testGetPointOfInterestListByPropertyId() {
        LiveData<List<PointOfInterestNearby>> pointOfInterestNearbyListLiveData = detailViewModel.getPointOfInterestListByPropertyId();
        assertSame(detailViewModel.getPointOfInterestListByPropertyId(), pointOfInterestNearbyListLiveData);
    }

    // ******************************
    // ******* PROPERTY PHOTO *******
    // ******************************
    @Test
    public void testInitPropertyPhotoListByPropertyIdList() {
        detailViewModel.initPropertyPhotoListByPropertyId(propertyById.getId());
        verify(propertyPhotoRepository).getPropertyPhotoByPropertyIdList(propertyById.getId());
    }

    @Test
    public void testGetPropertyPhotoListByPropertyId() {
        LiveData<List<PropertyPhoto>> propertyPhotoListLiveData = detailViewModel.getPropertyPhotoListByPropertyId();
        assertSame(detailViewModel.getPropertyPhotoListByPropertyId(), propertyPhotoListLiveData);
    }

    // ************************************
    // ******* PROPERTY SALE STATUS *******
    // ************************************
    @Test
    public void testInitPropertySaleStatus() {
        detailViewModel.initPropertySaleStatus();
        verify(propertySaleStatusRepository).getPropertySaleStatusList();
    }

    @Test
    public void testGetPropertySaleStatusList() {
        LiveData<List<PropertySaleStatus>> propertySaleStatusListLiveData = detailViewModel.getPropertySaleStatusList();
        assertSame(detailViewModel.getPropertySaleStatusList(), propertySaleStatusListLiveData);
    }

    // *****************************
    // ******* PROPERTY TYPE *******
    // *****************************
    @Test
    public void testInitPropertyType() {
        detailViewModel.initPropertyType();
        verify(propertyTypeRepository).getPropertyTypeList();
    }

    @Test
    public void testGetPropertyTypeList() {
        LiveData<List<PropertyType>> propertyTypeListLiveData = detailViewModel.getPropertyTypeList();
        assertSame(detailViewModel.getPropertyTypeList(), propertyTypeListLiveData);
    }

    // *********************************
    // ******* REAL ESTATE AGENT *******
    // *********************************

    @Test
    public void testInitRealEstateAgentList() {
        detailViewModel.initRealEstateAgentList();
        verify(realEstateAgentRepository).getRealEstateAgentList();
    }

    @Test
    public void testGetRealEstateAgentList() {
        LiveData<List<RealEstateAgent>> realEstateAgentListLiveData = detailViewModel.getRealEstateAgentList();
        assertSame(detailViewModel.getRealEstateAgentList(), realEstateAgentListLiveData);
    }


}
