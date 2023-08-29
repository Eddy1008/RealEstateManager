package com.openclassrooms.realestatemanager;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.openclassrooms.realestatemanager.add.AddPropertyViewModel;
import com.openclassrooms.realestatemanager.model.PointOfInterestNearby;
import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.model.PropertyPhoto;
import com.openclassrooms.realestatemanager.model.PropertyType;
import com.openclassrooms.realestatemanager.model.RealEstateAgent;
import com.openclassrooms.realestatemanager.repositories.PointOfInterestNearbyRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyPhotoRepository;
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
import java.util.concurrent.Executor;

public class AddPropertyViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    @Mock
    private Observer<Long> observer;
    @Mock
    private Executor executor;

    private AddPropertyViewModel addPropertyViewModel;

    private PointOfInterestNearbyRepository pointOfInterestNearbyRepository;
    private PropertyPhotoRepository propertyPhotoRepository;
    private PropertyRepository propertyRepository;
    private PropertyTypeRepository propertyTypeRepository;
    private RealEstateAgentRepository realEstateAgentRepository;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Create a mock MutableLiveData<List<PropertyType>>
        MutableLiveData<List<PropertyType>> mockLiveDataPropertyTypeList = new MutableLiveData<>();
        // Set the value you want to simulate (here empty list)
        List<PropertyType> mockPropertyTypeList = new ArrayList<>();
        mockLiveDataPropertyTypeList.setValue(mockPropertyTypeList);

        // Create a mock MutableLiveData<List<RealEstateAgent>>
        MutableLiveData<List<RealEstateAgent>> mockLiveDataRealEstateAgentList = new MutableLiveData<>();
        List<RealEstateAgent> mockRealEstateAgentList = new ArrayList<>();
        mockLiveDataRealEstateAgentList.setValue(mockRealEstateAgentList);

        pointOfInterestNearbyRepository = mock(PointOfInterestNearbyRepository.class);
        propertyPhotoRepository = mock(PropertyPhotoRepository.class);
        propertyRepository = mock(PropertyRepository.class);

        propertyTypeRepository = mock(PropertyTypeRepository.class);
        when(propertyTypeRepository.getPropertyTypeList()).thenReturn(mockLiveDataPropertyTypeList);

        realEstateAgentRepository = mock(RealEstateAgentRepository.class);
        when(realEstateAgentRepository.getRealEstateAgentList()).thenReturn(mockLiveDataRealEstateAgentList);

        // for add and deletePointOfInterest / propertyPhoto
        doAnswer(invocation -> {
            Runnable runnable = invocation.getArgument(0);
            runnable.run();
            return null;
        }).when(executor).execute(any(Runnable.class));

        // for add and deletePointOfInterest
        List<PointOfInterestNearby> mockPointOfInterestList = new ArrayList<>();
        doAnswer(invocation -> {
            PointOfInterestNearby pointOfInterest = invocation.getArgument(0);
            mockPointOfInterestList.add(pointOfInterest);
            return null;
        }).when(pointOfInterestNearbyRepository).createPointOfInterest(any(PointOfInterestNearby.class));

        // for add and deletePropertyPhoto
        List<PropertyPhoto> mockPropertyPhotoList = new ArrayList<>();
        doAnswer(invocation -> {
            PropertyPhoto propertyPhoto = invocation.getArgument(0);
            mockPropertyPhotoList.add(propertyPhoto);
            return null;
        }).when(propertyPhotoRepository).createPropertyPhoto(any(PropertyPhoto.class));

        addPropertyViewModel = new AddPropertyViewModel(
                pointOfInterestNearbyRepository, propertyPhotoRepository,propertyRepository,
                propertyTypeRepository, realEstateAgentRepository, executor);
    }

    // *****************************
    // ******* PROPERTY TYPE *******
    // *****************************
    @Test
    public void testInitPropertyTypeList() {
        addPropertyViewModel.initPropertyTypeList();
        verify(propertyTypeRepository).getPropertyTypeList();
    }

    @Test
    public void testGetPropertyTypeList() {
        LiveData<List<PropertyType>> propertyTypeListLiveData = addPropertyViewModel.getPropertyTypeList();
        assertSame(addPropertyViewModel.getPropertyTypeList(), propertyTypeListLiveData);
    }

    // *********************************
    // ******* REAL ESTATE AGENT *******
    // *********************************
    @Test
    public void testInitRealEstateAgentList() {
        addPropertyViewModel.initRealEstateAgentList();
        verify(realEstateAgentRepository).getRealEstateAgentList();
    }

    @Test
    public void testGetRealEstateAgentList() {
        LiveData<List<RealEstateAgent>> realEstateAgentListLiveData = addPropertyViewModel.getRealEstateAgentList();
        assertSame(addPropertyViewModel.getRealEstateAgentList(), realEstateAgentListLiveData);
    }

    // ************************
    // ******* PROPERTY *******
    // ************************

    @Test
    public void testInsertPropertyAndGetId() {
        // Créer un objet Property de test
        Property testProperty = new Property(
                "test A", "abcd", "efgh",
                "ijkl", "01/01/2013","01/01/2024",
                250000, 95, 6, 1, 3,
                1, 1, 1);

        // Configurer l'attente pour la méthode createProperty du propertyRepository
        MutableLiveData<Long> mockInsertedPropertyId = new MutableLiveData<>();
        long expectedPropertyId = 123;
        mockInsertedPropertyId.setValue(expectedPropertyId);
        when(propertyRepository.createProperty(testProperty)).thenReturn(mockInsertedPropertyId);
        addPropertyViewModel.insertPropertyAndGetId(testProperty).observeForever(observer);
        verify(propertyRepository).createProperty(testProperty);
        verify(observer).onChanged(expectedPropertyId);
    }

    // *********************************
    // ******* POINT OF INTEREST *******
    // *********************************

    @Test
    public void testAddAndDeletePointOfInterest() {
        // Créer un point d'intérêt de test
        PointOfInterestNearby testPointOfInterest = new PointOfInterestNearby("test", "abcd", 1);
        // Appeler la méthode addPointOfInterestToAddList
        addPropertyViewModel.addPointOfInterestToAddList(testPointOfInterest);
        // Vérifier que la liste de points d'intérêt a été mise à jour
        assertTrue(addPropertyViewModel.getListOfPointOfInterestToAdd().getValue().contains(testPointOfInterest));
        // Appeler la méthode deletePointOfInterestToAddList
        addPropertyViewModel.deletePointOfInterestToAddList(testPointOfInterest);
        // Vérifier que le point d'intérêt a été supprimé de la liste
        assertFalse(addPropertyViewModel.getListOfPointOfInterestToAdd().getValue().contains(testPointOfInterest));
    }

    @Test
    public void testAddPointOfInterest() {
        // Créer un point d'intérêt de test
        PointOfInterestNearby testPointOfInterest = new PointOfInterestNearby("test", "abcd", 1);
        // Appeler la méthode à tester
        addPropertyViewModel.addPointOfInterest(testPointOfInterest);
        // Vérifier que la méthode createPointOfInterest du repository a été appelée avec le point d'intérêt de test
        verify(pointOfInterestNearbyRepository).createPointOfInterest(testPointOfInterest);
        // Vérifier que l'Executor a été appelé pour exécuter la tâche
        verify(executor).execute(any(Runnable.class));
    }

    // ******************************
    // ******* PROPERTY PHOTO *******
    // ******************************

    @Test
    public void testAddAndDeletePropertyPhoto() {
        PropertyPhoto testPropertyPhoto = new PropertyPhoto("test", "abcd", 1);
        addPropertyViewModel.addPropertyPhotoToAddList(testPropertyPhoto);
        assertTrue(addPropertyViewModel.getListOfPropertyPhotoToAdd().getValue().contains(testPropertyPhoto));
        addPropertyViewModel.deletePropertyPhotoFromAddList(testPropertyPhoto);
        assertFalse(addPropertyViewModel.getListOfPropertyPhotoToAdd().getValue().contains(testPropertyPhoto));
    }

    @Test
    public void testAddPropertyPhoto() {
        PropertyPhoto testPropertyPhoto = new PropertyPhoto("test", "abcd", 1);
        addPropertyViewModel.addPropertyPhoto(testPropertyPhoto);
        verify(propertyPhotoRepository).createPropertyPhoto(testPropertyPhoto);
        verify(executor).execute(any(Runnable.class));
    }

}
