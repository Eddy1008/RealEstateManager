package com.openclassrooms.realestatemanager.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.openclassrooms.realestatemanager.database.dao.PointOfInterestNearbyDAO;
import com.openclassrooms.realestatemanager.database.dao.PropertyDAO;
import com.openclassrooms.realestatemanager.database.dao.PropertyPhotoDAO;
import com.openclassrooms.realestatemanager.database.dao.PropertySaleStatusDAO;
import com.openclassrooms.realestatemanager.database.dao.PropertyTypeDAO;
import com.openclassrooms.realestatemanager.database.dao.RealEstateAgentDAO;
import com.openclassrooms.realestatemanager.model.PointOfInterestNearby;
import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.model.PropertyPhoto;
import com.openclassrooms.realestatemanager.model.PropertySaleStatus;
import com.openclassrooms.realestatemanager.model.PropertyType;
import com.openclassrooms.realestatemanager.model.RealEstateAgent;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executors;

@Database(entities = {Property.class, PropertyType.class, PropertyPhoto.class, PropertySaleStatus.class, PointOfInterestNearby.class, RealEstateAgent.class}, version = 1, exportSchema = false)
public abstract class RealEstateManagerDatabase extends RoomDatabase {

    // Singleton
    private static volatile RealEstateManagerDatabase INSTANCE;

    // DAO
    public abstract PointOfInterestNearbyDAO pointOfInterestNearbyDAO();
    public abstract PropertyDAO propertyDAO();
    public abstract PropertyPhotoDAO propertyPhotoDAO();
    public abstract PropertySaleStatusDAO propertySaleStatusDAO();
    public abstract PropertyTypeDAO propertyTypeDAO();
    public abstract RealEstateAgentDAO realEstateAgentDAO();

    // Instance
    public static RealEstateManagerDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (RealEstateManagerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RealEstateManagerDatabase.class, "REMDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback prepopulateDatabase() {
        return new Callback() {
            @Override
            public void onCreate(@NotNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                // TODO Prepopulate: Add photo in database
                Executors.newSingleThreadExecutor().execute(() -> {
                    // PROPERTY SALE  STATUS
                    INSTANCE.propertySaleStatusDAO().createPropertySaleStatus(new PropertySaleStatus(1, "For sale"));
                    INSTANCE.propertySaleStatusDAO().createPropertySaleStatus(new PropertySaleStatus(2, "Sold"));
                    // PROPERTY TYPE
                    INSTANCE.propertyTypeDAO().createPropertyType(new PropertyType(1, "Loft"));
                    INSTANCE.propertyTypeDAO().createPropertyType(new PropertyType(2, "House"));
                    INSTANCE.propertyTypeDAO().createPropertyType(new PropertyType(3, "Duplex"));
                    INSTANCE.propertyTypeDAO().createPropertyType(new PropertyType(4, "Penthouse"));
                    INSTANCE.propertyTypeDAO().createPropertyType(new PropertyType(5, "Flat"));
                    // REAL ESTATE AGENT
                    INSTANCE.realEstateAgentDAO().createRealEstateAgent(new RealEstateAgent(1, "Eddy", "Eddy@gmail.com", "", "0123456789"));
                    INSTANCE.realEstateAgentDAO().createRealEstateAgent(new RealEstateAgent(2, "Ethan", "Ethan@gmail.com", "", "9876543210"));
                    // PROPERTY
                    long firstPropertyId = INSTANCE.propertyDAO().createProperty(
                            new Property("test 1", "147 sentier de l'église 59320 hallennes-lez-haubourdin",
                                    "https://cdn.pixabay.com/photo/2017/08/30/01/05/milky-way-2695569_960_720.jpg",
                                    "BLABLA blablabla BLABLA blablabla BLABLA blablabla", "04/08/2023", "",
                                    300000, 175, 10, 3, 3, 2, 1,1)
                    );
                    long secondPropertyId = INSTANCE.propertyDAO().createProperty(
                            new Property("test 2", "48 rue de l'hirondelle 59320 hallennes-lez-haubourdin",
                                    "https://cdn.pixabay.com/photo/2016/09/05/18/54/texture-1647380_960_720.jpg",
                                    "PATATI ET PATATA , PATATI ET PATATA , PATATI ET PATATA , PATATI ET PATATA , PATATI ET PATATA , PATATI ET PATATA", "02/08/2023", "",
                                    450000, 210, 8, 2, 4, 1, 1, 1)
                    );
                    // POINT OF INTEREST NEARBY
                    INSTANCE.pointOfInterestNearbyDAO().createPointOfInterest(new PointOfInterestNearby("Boulangerie", "143 sentier de l'église 59320 hallennes-lez-haubourdin", firstPropertyId));
                    INSTANCE.pointOfInterestNearbyDAO().createPointOfInterest(new PointOfInterestNearby("Boucherie", "151 sentier de l'église 59320 hallennes-lez-haubourdin", firstPropertyId));
                    INSTANCE.pointOfInterestNearbyDAO().createPointOfInterest(new PointOfInterestNearby("Boulangerie", "143 sentier de l'église 59320 hallennes-lez-haubourdin", secondPropertyId));
                    // PHOTO
                });
            }
        };
    }

}
