package com.openclassrooms.realestatemanager.detail.ui.itemtodetail;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.BuildConfig;
import com.openclassrooms.realestatemanager.ViewModelFactory;
import com.openclassrooms.realestatemanager.databinding.FragmentItemBinding;
import com.openclassrooms.realestatemanager.detail.DetailViewModel;
import com.openclassrooms.realestatemanager.model.PointOfInterestNearby;
import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.model.PropertyPhoto;

import java.util.ArrayList;
import java.util.List;

public class ItemFragment extends Fragment {

    private FragmentItemBinding binding;
    private DetailViewModel detailViewModel;
    private RecyclerView propertyPhotoRecyclerView;
    private PropertyPhotoAdapter propertyPhotoAdapter;
    private List<PropertyPhoto> propertyPhotoList = new ArrayList<>();
    private RecyclerView pointOfInterestRecyclerView;
    private PointOfInterestAdapter pointOfInterestAdapter;
    private List<PointOfInterestNearby> pointOfInterestList = new ArrayList<>();
    private Property item;
    private String itemLocation;
    private String itemMiniMapUrl;

    public ItemFragment() {
    }

    public static ItemFragment newInstance(Property property) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putSerializable("PROPERTY_ITEM", property);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentItemBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        detailViewModel = new ViewModelProvider(requireActivity(), ViewModelFactory.getInstance(getContext())).get(DetailViewModel.class);
        getMyProperty();

        // TODO POSSIBILITE DE METTRE A JOUR LES INFORMATIONS SAISIES

        // TODO AFFICHER LE BIEN "VENDU"

        return root;
    }

    private void getMyProperty() {
        detailViewModel.getMyProperty().observe(getViewLifecycleOwner(), property -> {
            item = property;
            String title = item.getTitle() + " id = " + item.getId();
            binding.fragmentItemTitle.setText(title);
            binding.fragmentItemTextviewDescription.setText(item.getPropertyDescription());
            binding.fragmentItemCardViewSurface.setText(String.valueOf(item.getPropertySurface()));
            binding.fragmentItemCardViewNumberRoom.setText(String.valueOf(item.getRoomNumber()));
            binding.fragmentItemCardViewNumberBathroom.setText(String.valueOf(item.getBathroomNumber()));
            binding.fragmentItemCardViewNumberBedroom.setText(String.valueOf(item.getBedroomNumber()));
            binding.fragmentItemCardViewLocation.setText(item.getAddress());

            itemLocation = convertAddressToLatLng(item.getAddress());

            detailViewModel.initPropertyPhotoListByPropertyId(item.getId());
            configurePropertyPhotoRecyclerView();

            detailViewModel.initPointOfInterestListByPropertyId(item.getId());
            configurePointOfInterestRecyclerView();

        });
    }

    private void configurePropertyPhotoRecyclerView() {
        propertyPhotoRecyclerView = binding.fragmentItemPropertyPhotoRecyclerview;
        propertyPhotoRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        detailViewModel.getPropertyPhotoListByPropertyId().observe(getViewLifecycleOwner(), propertyPhotos -> {
            propertyPhotoList = propertyPhotos;
            propertyPhotoAdapter = new PropertyPhotoAdapter(propertyPhotoList);
            propertyPhotoRecyclerView.setAdapter(propertyPhotoAdapter);
        });
    }

    private void configurePointOfInterestRecyclerView() {
        pointOfInterestRecyclerView = binding.fragmentItemPointOfInterestRecyclerview;
        pointOfInterestRecyclerView.setLayoutManager(new LinearLayoutManager(pointOfInterestRecyclerView.getContext()));
        pointOfInterestRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        detailViewModel.getPointOfInterestListByPropertyId().observe(getViewLifecycleOwner(), pointOfInterestNearbies -> {
            pointOfInterestList = pointOfInterestNearbies;
            pointOfInterestAdapter = new PointOfInterestAdapter(pointOfInterestList);
            pointOfInterestRecyclerView.setAdapter(pointOfInterestAdapter);

            configureMiniMap(pointOfInterestList);
        });
    }

    private void configureMiniMap(List<PointOfInterestNearby> pointOfInterestList) {
        itemMiniMapUrl = "https://maps.googleapis.com/maps/api/staticmap?center=" + itemLocation + "&zoom=16&size=400x400"
                + "&markers=color:blue%7Clabel:S%7C" + itemLocation;
        char alphabet = 'A';
        for (int i=0; i<pointOfInterestList.size(); i++) {
            String pointLocation = convertAddressToLatLng(pointOfInterestList.get(i).getAddress());
            itemMiniMapUrl = itemMiniMapUrl + "&markers=color:0xFFFF00%7Clabel:" + alphabet + "%7C" + pointLocation;
            alphabet++;
        }

        itemMiniMapUrl = itemMiniMapUrl + "&key=" + BuildConfig.MAPS_API_KEY;

        Glide.with(binding.fragmentItemCardViewMiniMap)
                .load(itemMiniMapUrl)
                .into(binding.fragmentItemCardViewMiniMap);
    }

    public String convertAddressToLatLng(String address) {
        String addressLatLng = "";
        if (address != null && !address.isEmpty()) {
            try {
                Geocoder coder = new Geocoder(getContext());
                List<Address> addressList = coder.getFromLocationName(address,1);
                if (addressList != null && addressList.size()>0) {
                    addressLatLng = addressList.get(0).getLatitude() + "," + addressList.get(0).getLongitude();
                }
            } catch (Exception e) {
                Log.e("TAG", "convertAddressToLatLng: ", e );
            }
        }
        return addressLatLng;
    }
}
