package com.openclassrooms.realestatemanager.main.ui.mapview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ViewModelFactory;
import com.openclassrooms.realestatemanager.databinding.FragmentMapviewBinding;
import com.openclassrooms.realestatemanager.detail.DetailActivity;
import com.openclassrooms.realestatemanager.detail.DetailViewModel;
import com.openclassrooms.realestatemanager.detail.ui.itemtodetail.ItemFragment;
import com.openclassrooms.realestatemanager.main.MainViewModel;
import com.openclassrooms.realestatemanager.model.Property;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MapviewFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;
    // Default location
    private final LatLng defaultLocation = new LatLng(50.613424, 2.966952);
    // location provider
    private FusedLocationProviderClient fusedLocationProviderClient;
    // Default zoom
    private static final int DEFAULT_ZOOM = 14;
    private boolean locationPermissionGranted;
    private Location lastKnownLocation;
    private MainViewModel mainViewModel;
    private FragmentMapviewBinding binding;
    private boolean isTwoPane;
    private List<Property> propertyList = new ArrayList<>();
    private final List<Marker> markers = new ArrayList<>();

    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if (result) {
                        locationPermissionGranted = true;
                        updateLocationUI();
                        getDeviceLocation();
                    } else {
                        locationPermissionGranted = false;
                    }
                }
            }
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentMapviewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mainViewModel = new ViewModelProvider(requireActivity(), ViewModelFactory.getInstance(getContext())).get(MainViewModel.class);
        mainViewModel.initPropertyList();
        mainViewModel.initPropertyTypeList();

        // FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getActivity());

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_mapview);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        binding.fabFindMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMapReady(map);
            }
        });

        return root;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(new MarkerOptions().position(defaultLocation).title("Home by default"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, DEFAULT_ZOOM));

        getPropertyList();

        getLocationPermission();
        updateLocationUI();
        getDeviceLocation();
    }

    /**
     * Prompts the user for permission to use the device location.
     */
    private void getLocationPermission() {
        requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION);
    }


    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    private void getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                @SuppressLint("MissingPermission") Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            }
                        } else {
                            Log.e("TAG", "Exception: %s", task.getException());
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            map.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    @SuppressLint("MissingPermission")
    private void updateLocationUI() {
        if (map == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(false);
            } else {
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    public void getPropertyList() {
        mainViewModel.getIsTwoPaneMode().observe(getViewLifecycleOwner(), aBoolean -> {
            isTwoPane = aBoolean;
            mainViewModel.getPropertyList().observe(getViewLifecycleOwner(), properties -> {
                propertyList = new ArrayList<>(properties);
                for (Marker marker : markers) {
                    marker.remove();
                }
                markers.clear();
                for (Property property : propertyList) {
                    switch ((int) property.getPropertyTypeId()) {
                        case 1:
                            Marker markerGreen = map.addMarker(new MarkerOptions()
                                    .position(convertAddressToLatLng(property.getAddress()))
                                    .title(property.getTitle())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            if (markerGreen != null) {
                                markerGreen.setTag(property);
                            }
                            markers.add(markerGreen);
                            break;
                        case 2:
                            Marker markerBlue = map.addMarker(new MarkerOptions()
                                    .position(convertAddressToLatLng(property.getAddress()))
                                    .title(property.getTitle())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                            if (markerBlue != null) {
                                markerBlue.setTag(property);
                            }
                            markers.add(markerBlue);
                            break;
                        case 3:
                            Marker markerRed = map.addMarker(new MarkerOptions()
                                    .position(convertAddressToLatLng(property.getAddress()))
                                    .title(property.getTitle())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            if (markerRed != null) {
                                markerRed.setTag(property);
                            }
                            markers.add(markerRed);
                            break;
                        case 4:
                            Marker markerOrange = map.addMarker(new MarkerOptions()
                                    .position(convertAddressToLatLng(property.getAddress()))
                                    .title(property.getTitle())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                            if (markerOrange != null) {
                                markerOrange.setTag(property);
                            }
                            markers.add(markerOrange);
                            break;
                        case 5:
                            Marker markerViolet = map.addMarker(new MarkerOptions()
                                    .position(convertAddressToLatLng(property.getAddress()))
                                    .title(property.getTitle())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
                            if (markerViolet != null) {
                                markerViolet.setTag(property);
                            }
                            markers.add(markerViolet);
                            break;
                        default:
                            Marker markerYellow = map.addMarker(new MarkerOptions()
                                    .position(convertAddressToLatLng(property.getAddress()))
                                    .title(property.getTitle())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                            if (markerYellow != null) {
                                markerYellow.setTag(property);
                            }
                            markers.add(markerYellow);
                            break;
                    }
                }

                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        if (isTwoPane) {
                            showItemInFragment((Property) marker.getTag());
                        } else {
                            Intent intent = new Intent(getContext(), DetailActivity.class);
                            Bundle myBundle = new Bundle();
                            myBundle.putSerializable("PROPERTY_ITEM", (Serializable) marker.getTag());
                            intent.putExtra("BUNDLE_ITEM_TO_DETAIL", myBundle);
                            getContext().startActivity(intent);
                        }
                        return false;
                    }
                });
            });
        });

    }

    private void showItemInFragment(Property property) {
        // Update the DetailViewModel with the selected item information.
        DetailViewModel detailViewModel = new ViewModelProvider((AppCompatActivity) getContext(), ViewModelFactory.getInstance(getContext())).get(DetailViewModel.class);
        detailViewModel.setMyProperty(property);

        // Create an instance of the ItemFragment and set its arguments.
        FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
        ItemFragment itemFragment = ItemFragment.newInstance(property);

        // Replace the content of the detailContainer with the ItemFragment.
        fragmentManager.beginTransaction()
                .replace(R.id.detailContainer, itemFragment)
                .commit();
    }

    public LatLng convertAddressToLatLng(String address) {
        LatLng addressLatLng = new LatLng(0,0);
        if (address != null && !address.isEmpty()) {
            try {
                Geocoder coder = new Geocoder(getContext());
                List<Address> addressList = coder.getFromLocationName(address,1);
                if (addressList != null && addressList.size()>0) {
                    addressLatLng = new LatLng(addressList.get(0).getLatitude(), addressList.get(0).getLongitude());
                }
            } catch (Exception e) {
                Log.e("TAG", "convertAddressToLatLng: ", e );
            }
        }
        return addressLatLng;
    }
}
