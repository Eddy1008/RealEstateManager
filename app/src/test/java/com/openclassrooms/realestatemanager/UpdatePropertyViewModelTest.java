package com.openclassrooms.realestatemanager;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
import com.openclassrooms.realestatemanager.update.UpdatePropertyViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class UpdatePropertyViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    @Mock
    private Executor executor;

    private UpdatePropertyViewModel updatePropertyViewModel;

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

        // for add and deletePointOfInterest / propertyPhoto
        doAnswer(invocation -> {
            Runnable runnable = invocation.getArgument(0);
            runnable.run();
            return null;
        }).when(executor).execute(any(Runnable.class));

        updatePropertyViewModel = new UpdatePropertyViewModel(
                pointOfInterestNearbyRepository, propertyPhotoRepository, propertyRepository,
                propertyTypeRepository, realEstateAgentRepository, executor);
    }

    // *****************************
    // ******* PROPERTY TYPE *******
    // *****************************
    @Test
    public void testInitPropertyTypeList() {
        updatePropertyViewModel.initPropertyTypeList();
        verify(propertyTypeRepository).getPropertyTypeList();
    }

    @Test
    public void testGetPropertyTypeList() {
        LiveData<List<PropertyType>> propertyTypeListLiveData = updatePropertyViewModel.getPropertyTypeList();
        assertSame(updatePropertyViewModel.getPropertyTypeList(), propertyTypeListLiveData);
    }

    // *********************************
    // ******* REAL ESTATE AGENT *******
    // *********************************
    @Test
    public void testInitRealEstateAgentList() {
        updatePropertyViewModel.initRealEstateAgentList();
        verify(realEstateAgentRepository).getRealEstateAgentList();
    }

    @Test
    public void testGetRealEstateAgentList() {
        LiveData<List<RealEstateAgent>> realEstateAgentListLiveData = updatePropertyViewModel.getRealEstateAgentList();
        assertSame(updatePropertyViewModel.getRealEstateAgentList(), realEstateAgentListLiveData);
    }

    // ************************
    // ******* PROPERTY *******
    // ************************

    @Test
    public void testUpdateProperty() {
        Property testProperty = new Property(
                "test", "test", "test", "test",
                "test", "test", 100000 , 75,
                6, 1, 3,
                1, 1, 1);
        updatePropertyViewModel.updateProperty(testProperty);
        verify(propertyRepository).updateProperty(testProperty);
        verify(executor).execute(any(Runnable.class));
    }

    // *********************************
    // ******* POINT OF INTEREST *******
    // *********************************

    @Test
    public void testInitPointOfInterestList() {
        updatePropertyViewModel.initPointOfInterestList(propertyById.getId());
        verify(pointOfInterestNearbyRepository).getPointOfInterestNearbyByPropertyIdList(propertyById.getId());
    }

    @Test
    public void testGetPointOfInterestList() {
        LiveData<List<PointOfInterestNearby>> pointOfInterestListLiveData = updatePropertyViewModel.getPointOfInterestList();
        assertSame(updatePropertyViewModel.getPointOfInterestList(), pointOfInterestListLiveData);
    }

    @Test
    public void testAddPointOfInterest() {
        PointOfInterestNearby testPointOfInterest = new PointOfInterestNearby("test", "abcd", 1);
        updatePropertyViewModel.addPointOfInterest(testPointOfInterest);
        verify(pointOfInterestNearbyRepository).createPointOfInterest(testPointOfInterest);
        verify(executor).execute(any(Runnable.class));
    }

    @Test
    public void testDeletePointOfInterest() {
        PointOfInterestNearby testPointOfInterest = new PointOfInterestNearby("test", "abcd", 1);
        updatePropertyViewModel.deletePointOfInterest(testPointOfInterest);
        verify(pointOfInterestNearbyRepository).deletePointOfInterestNearby(testPointOfInterest.getId());
        verify(executor).execute(any(Runnable.class));
    }


    // ******************************
    // ******* PROPERTY PHOTO *******
    // ******************************
    @Test
    public void testInitPropertyPhotoList() {
        updatePropertyViewModel.initPropertyPhotoList(propertyById.getId());
        verify(propertyPhotoRepository).getPropertyPhotoByPropertyIdList(propertyById.getId());
    }

    @Test
    public void testGetPropertyPhotoList() {
        LiveData<List<PropertyPhoto>> propertyPhotoListLiveData = updatePropertyViewModel.getPropertyPhotoList();
        assertSame(updatePropertyViewModel.getPropertyPhotoList(), propertyPhotoListLiveData);
    }

    @Test
    public void testAddPropertyPhoto() {
        PropertyPhoto testPropertyPhoto = new PropertyPhoto("test", "abcd", 1);
        updatePropertyViewModel.addPropertyPhoto(testPropertyPhoto);
        verify(propertyPhotoRepository).createPropertyPhoto(testPropertyPhoto);
        verify(executor).execute(any(Runnable.class));
    }

    @Test
    public void testDeletePropertyPhoto() {
        PropertyPhoto testPropertyPhoto = new PropertyPhoto("test", "abcd", 1);
        updatePropertyViewModel.deletePropertyPhoto(testPropertyPhoto);
        verify(propertyPhotoRepository).deletePropertyPhoto(testPropertyPhoto.getId());
        verify(executor).execute(any(Runnable.class));
    }
}
