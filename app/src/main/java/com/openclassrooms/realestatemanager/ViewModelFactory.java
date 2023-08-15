package com.openclassrooms.realestatemanager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.add.AddPropertyViewModel;
import com.openclassrooms.realestatemanager.detail.DetailViewModel;
import com.openclassrooms.realestatemanager.injection.Di;
import com.openclassrooms.realestatemanager.main.MainViewModel;
import com.openclassrooms.realestatemanager.repositories.PointOfInterestNearbyRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyPhotoRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;
import com.openclassrooms.realestatemanager.repositories.PropertySaleStatusRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyTypeRepository;
import com.openclassrooms.realestatemanager.repositories.RealEstateAgentRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final PointOfInterestNearbyRepository pointOfInterestNearbyRepository;
    private final PropertyPhotoRepository propertyPhotoRepository;
    private final PropertyRepository propertyRepository;
    private final PropertySaleStatusRepository propertySaleStatusRepository;
    private final PropertyTypeRepository propertyTypeRepository;
    private final RealEstateAgentRepository realEstateAgentRepository;
    private final Executor executor;

    private static ViewModelFactory factory;

    public static  ViewModelFactory getInstance(Context context) {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory(context);
                }
            }
        }
        return factory;
    }

    private ViewModelFactory(Context context) {
        this.pointOfInterestNearbyRepository = Di.getPointOfInterestNearbyRepository(context);
        this.propertyPhotoRepository = Di.getPropertyPhotoRepository(context);
        this.propertyRepository = Di.getPropertyRepository(context);
        this.propertySaleStatusRepository = Di.getPropertySaleStatusRepository(context);
        this.propertyTypeRepository = Di.getPropertyTypeRepository(context);
        this.realEstateAgentRepository = Di.getRealEstateAgentRepository(context);
        this.executor = Executors.newSingleThreadExecutor();
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DetailViewModel.class)) {
            return (T) new DetailViewModel(pointOfInterestNearbyRepository, propertyPhotoRepository, propertyRepository, propertySaleStatusRepository, propertyTypeRepository, realEstateAgentRepository, executor);
        }

        if (modelClass.isAssignableFrom(AddPropertyViewModel.class)) {
            return (T) new AddPropertyViewModel(pointOfInterestNearbyRepository, propertyPhotoRepository, propertyRepository, propertySaleStatusRepository, propertyTypeRepository, realEstateAgentRepository, executor);
        }

        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(propertyRepository, propertyTypeRepository, executor);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
