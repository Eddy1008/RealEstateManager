package com.openclassrooms.realestatemanager.injection;

import android.content.Context;

import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase;
import com.openclassrooms.realestatemanager.repositories.PointOfInterestNearbyRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyPhotoRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;
import com.openclassrooms.realestatemanager.repositories.PropertySaleStatusRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyTypeRepository;
import com.openclassrooms.realestatemanager.repositories.RealEstateAgentRepository;

public class Di {

    public static boolean isForTesting = false;

    static PointOfInterestNearbyRepository pointOfInterestNearbyRepository = null;
    static PropertyPhotoRepository propertyPhotoRepository = null;
    static PropertyRepository propertyRepository = null;
    static PropertySaleStatusRepository propertySaleStatusRepository = null;
    static PropertyTypeRepository propertyTypeRepository = null;
    static RealEstateAgentRepository realEstateAgentRepository = null;

    public static PointOfInterestNearbyRepository getPointOfInterestNearbyRepository(Context context) {
        if (pointOfInterestNearbyRepository == null) {
            if (isForTesting) {
                // RealEstateManagerDatabase database = RealEstateManagerDatabase.getInstance(context);
                // pointOfInterestNearbyRepository = new PointOfInterestNearbyRepository(database.pointOfInterestNearbyDAO());
            } else {
                RealEstateManagerDatabase database = RealEstateManagerDatabase.getInstance(context);
                pointOfInterestNearbyRepository = new PointOfInterestNearbyRepository(database.pointOfInterestNearbyDAO());
            }
        }
        return pointOfInterestNearbyRepository;
    }

    public static PropertyPhotoRepository getPropertyPhotoRepository(Context context) {
        if (propertyPhotoRepository == null) {
            if (isForTesting) {
                // RealEstateManagerDatabase database = RealEstateManagerDatabase.getInstance(context);
                // propertyPhotoRepository = new PropertyPhotoRepository(database.propertyPhotoDAO());
            } else {
                RealEstateManagerDatabase database = RealEstateManagerDatabase.getInstance(context);
                propertyPhotoRepository = new PropertyPhotoRepository(database.propertyPhotoDAO());
            }
        }
        return propertyPhotoRepository;
    }

    public static PropertyRepository getPropertyRepository(Context context) {
        if (propertyRepository == null) {
            if (isForTesting) {
                // RealEstateManagerDatabase database = RealEstateManagerDatabase.getInstance(context);
                // propertyRepository = new PropertyRepository(database.propertyDAO());
            } else {
                RealEstateManagerDatabase database = RealEstateManagerDatabase.getInstance(context);
                propertyRepository = new PropertyRepository(database.propertyDAO());
            }
        }
        return propertyRepository;
    }

    public static PropertySaleStatusRepository getPropertySaleStatusRepository(Context context) {
        if (propertySaleStatusRepository == null) {
            if (isForTesting) {
                // RealEstateManagerDatabase database = RealEstateManagerDatabase.getInstance(context);
                // propertySaleStatusRepository = new PropertySaleStatusRepository(database.propertySaleStatusDAO());
            } else {
                RealEstateManagerDatabase database = RealEstateManagerDatabase.getInstance(context);
                propertySaleStatusRepository = new PropertySaleStatusRepository(database.propertySaleStatusDAO());
            }
        }
        return propertySaleStatusRepository;
    }

    public static PropertyTypeRepository getPropertyTypeRepository(Context context) {
        if (propertyTypeRepository == null) {
            if (isForTesting) {
                // RealEstateManagerDatabase database = RealEstateManagerDatabase.getInstance(context);
                // propertyTypeRepository = new PropertyTypeRepository(database.propertyTypeDAO());
            } else {
                RealEstateManagerDatabase database = RealEstateManagerDatabase.getInstance(context);
                propertyTypeRepository = new PropertyTypeRepository(database.propertyTypeDAO());
            }
        }
        return propertyTypeRepository;
    }

    public static RealEstateAgentRepository getRealEstateAgentRepository(Context context) {
        if (realEstateAgentRepository == null) {
            if (isForTesting) {
                // RealEstateManagerDatabase database = RealEstateManagerDatabase.getInstance(context);
                // realEstateAgentRepository = new RealEstateAgentRepository(database.realEstateAgentDAO());
            } else {
                RealEstateManagerDatabase database = RealEstateManagerDatabase.getInstance(context);
                realEstateAgentRepository = new RealEstateAgentRepository(database.realEstateAgentDAO());
            }
        }
        return realEstateAgentRepository;
    }
}
