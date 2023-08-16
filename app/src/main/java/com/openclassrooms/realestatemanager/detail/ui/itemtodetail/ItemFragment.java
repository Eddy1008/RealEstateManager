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

import java.util.ArrayList;
import java.util.List;

public class ItemFragment extends Fragment {

    private FragmentItemBinding binding;
    private DetailViewModel detailViewModel;
    private RecyclerView recyclerView;
    private PointOfInterestAdapter pointOfInterestAdapter;
    private List<PointOfInterestNearby> pointOfInterestList = new ArrayList<>();
    private Property item;
    private String propertyLat;
    private String propertyLng;

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

        return root;
    }

    private void getMyProperty() {
        detailViewModel.getMyProperty().observe(getViewLifecycleOwner(), property -> {
            item = property;
            String title = item.getTitle() + " id = " + item.getId();
            binding.fragmentItemTitle.setText(title);
            Glide.with(binding.fragmentItemImageviewPhoto)
                    .load(item.getMainPhoto())
                    .into(binding.fragmentItemImageviewPhoto);
            binding.fragmentItemTextviewDescription.setText(item.getPropertyDescription());
            binding.fragmentItemCardViewSurface.setText(String.valueOf(item.getPropertySurface()));
            binding.fragmentItemCardViewNumberRoom.setText(String.valueOf(item.getRoomNumber()));
            binding.fragmentItemCardViewNumberBathroom.setText(String.valueOf(item.getBathroomNumber()));
            binding.fragmentItemCardViewNumberBedroom.setText(String.valueOf(item.getBedroomNumber()));
            binding.fragmentItemCardViewLocation.setText(item.getAddress());

            // TODO Afficher la map miniature ...
            String pointOfInterestLat = " 50.612427";
            String pointOfInterestLng = "2.967424";
            convertAddressToLatLng(item.getAddress());
            String myUri = "https://maps.googleapis.com/maps/api/staticmap?center=" + propertyLat + "," + propertyLng + "&zoom=15&size=300x300"
                    + "&markers=color:blue%7Clabel:S%7C" + propertyLat + "," + propertyLng
                    + "&markers=size:mid%7Ccolor:0xFFFF00%7Clabel:C%7C" + pointOfInterestLat + "," + pointOfInterestLng
                    + "&key=" + BuildConfig.MAPS_API_KEY;

            Glide.with(binding.fragmentItemCardViewMiniMap)
                    .load(myUri)
                    .into(binding.fragmentItemCardViewMiniMap);

            detailViewModel.initPointOfInterestListByPropertyId(item.getId());
            configurePointOfInterestRecyclerView();
        });
    }

    private void configurePointOfInterestRecyclerView() {
        recyclerView = binding.fragmentItemPointOfInterestRecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        detailViewModel.getPointOfInterestListByPropertyId().observe(getViewLifecycleOwner(), pointOfInterestNearbies -> {
            pointOfInterestList = pointOfInterestNearbies;
            Log.d("TAG", "configurePointOfInterestRecyclerView: pointOfInterestList.size() = " + pointOfInterestList.size());
            pointOfInterestAdapter = new PointOfInterestAdapter(pointOfInterestList);
            recyclerView.setAdapter(pointOfInterestAdapter);
        });
    }


    public void convertAddressToLatLng(String address) {
        if (address != null && !address.isEmpty()) {
            try {
                Geocoder coder = new Geocoder(getContext());
                List<Address> addressList = coder.getFromLocationName(address,1);
                if (addressList != null && addressList.size()>0) {
                    propertyLat = String.valueOf(addressList.get(0).getLatitude());
                    propertyLng = String.valueOf(addressList.get(0).getLongitude());
                }
            } catch (Exception e) {
                Log.e("TAG", "convertAddressToLatLng: ", e );
            }
        }
    }
}
