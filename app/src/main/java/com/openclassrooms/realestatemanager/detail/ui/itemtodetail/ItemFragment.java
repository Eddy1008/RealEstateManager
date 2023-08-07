package com.openclassrooms.realestatemanager.detail.ui.itemtodetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.ViewModelFactory;
import com.openclassrooms.realestatemanager.databinding.FragmentItemBinding;
import com.openclassrooms.realestatemanager.detail.DetailViewModel;
import com.openclassrooms.realestatemanager.model.Property;

public class ItemFragment extends Fragment {

    private FragmentItemBinding binding;
    private DetailViewModel detailViewModel;
    private Property item;

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

        detailViewModel.getMyProperty().observe(getViewLifecycleOwner(), property -> {
            item = property;
            binding.fragmentItemTitle.setText(item.getTitle());
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
            Glide.with(binding.fragmentItemCardViewMiniMap)
                    .load(item.getMainPhoto())
                    .into(binding.fragmentItemCardViewMiniMap);
        });

        return root;
    }
}
