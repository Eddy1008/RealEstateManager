package com.openclassrooms.realestatemanager.detail.ui.itemtodetail;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.BuildConfig;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ViewModelFactory;
import com.openclassrooms.realestatemanager.databinding.FragmentItemBinding;
import com.openclassrooms.realestatemanager.detail.DetailViewModel;
import com.openclassrooms.realestatemanager.model.PointOfInterestNearby;
import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.model.PropertyPhoto;
import com.openclassrooms.realestatemanager.model.PropertySaleStatus;
import com.openclassrooms.realestatemanager.model.PropertyType;
import com.openclassrooms.realestatemanager.model.RealEstateAgent;
import com.openclassrooms.realestatemanager.update.UpdatePropertyActivity;

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

    private List<PropertySaleStatus> propertySaleStatusList = new ArrayList<>();
    private List<PropertyType> propertyTypeList = new ArrayList<>();
    private List<RealEstateAgent> realEstateAgentList = new ArrayList<>();

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
        detailViewModel.initPropertyType();
        detailViewModel.initPropertySaleStatus();
        detailViewModel.initRealEstateAgentList();
        detailViewModel.initPropertyType();
        getMyProperty();

        return root;
    }

    private void getMyProperty() {
        detailViewModel.getMyProperty().observe(getViewLifecycleOwner(), property -> {
            item = property;
            binding.fragmentItemTitle.setText(item.getTitle());
            binding.fragmentItemOnSaleDateTextview.setText(item.getOnSaleDate());
            binding.fragmentItemTextviewSoldDate.setText(item.getSaleDealDate());
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

            getPropertySaleStatus();
            getRealEstateAgent();
            getPropertyType();
            setUpdateButton();
        });
    }

    private void getPropertySaleStatus() {
        detailViewModel.getPropertySaleStatusList().observe(getViewLifecycleOwner(), propertySaleStatuses -> {
            propertySaleStatusList = propertySaleStatuses;
            String propertyStatutName = "";
            for (PropertySaleStatus status : propertySaleStatusList) {
                if (item.getPropertySaleStatusId() == status.getId()) {
                    propertyStatutName = status.getName();
                }
            }
            if (propertyStatutName.equals("Sold")) {
                int color = ContextCompat.getColor(getContext(), R.color.fragment_item_sold);
                binding.fragmentItemSaleStatus.setTextColor(color);
                binding.fragmentItemTextviewSoldTitle.setVisibility(View.VISIBLE);
                binding.fragmentItemTextviewSoldDate.setVisibility(View.VISIBLE);
            } else {
                int color = ContextCompat.getColor(getContext(), R.color.fragment_item_for_sale);
                binding.fragmentItemSaleStatus.setTextColor(color);
                binding.fragmentItemSaleStatus.setText(propertyStatutName);
                binding.fragmentItemTextviewSoldTitle.setVisibility(View.GONE);
                binding.fragmentItemTextviewSoldDate.setVisibility(View.GONE);
            }
            binding.fragmentItemSaleStatus.setText(propertyStatutName);

        });
    }

    private void getRealEstateAgent() {
        detailViewModel.getRealEstateAgentList().observe(getViewLifecycleOwner(), realEstateAgents -> {
            realEstateAgentList = realEstateAgents;
            for (RealEstateAgent agent : realEstateAgentList) {
                if (item.getRealEstateAgentId() == agent.getId()) {
                    binding.fragmentItemAgentTextview.setText(agent.getName());
                }
            }
        });
    }

    private void getPropertyType() {
        detailViewModel.getPropertyTypeList().observe(getViewLifecycleOwner(), propertyTypes -> {
            propertyTypeList = propertyTypes;
            for (PropertyType type : propertyTypeList) {
                if (item.getPropertyTypeId() == type.getId()) {
                    binding.fragmentItemTextviewType.setText(type.getName());
                }
            }
        });
    }

    private void setUpdateButton() {
        binding.fragmentItemUpdateImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UpdatePropertyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("PROPERTY_ITEM_TO_UPDATE", item);
                intent.putExtra("BUNDLE_ITEM_TO_UPDATE", bundle);
                view.getContext().startActivity(intent);
            }
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
