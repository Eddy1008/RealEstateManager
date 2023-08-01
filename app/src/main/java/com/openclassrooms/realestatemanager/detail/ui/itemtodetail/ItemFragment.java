package com.openclassrooms.realestatemanager.detail.ui.itemtodetail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.ViewModelFactory;
import com.openclassrooms.realestatemanager.databinding.FragmentItemToDetailBinding;
import com.openclassrooms.realestatemanager.detail.DetailViewModel;

public class ItemFragment extends Fragment {

    private FragmentItemToDetailBinding binding;

    private DetailViewModel detailViewModel;

    private String itemInfo;
    private String itemPhoto;

    public ItemFragment() {
    }

    public static ItemFragment newInstance(String itemInfo, String itemPhoto) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putString("ITEM_INFO", itemInfo);
        args.putString("ITEM_PHOTO", itemPhoto);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            itemInfo = getArguments().getString("ITEM_INFO");
            itemPhoto = getArguments().getString("ITEM_PHOTO");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentItemToDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        detailViewModel = new ViewModelProvider(requireActivity(), ViewModelFactory.getInstance()).get(DetailViewModel.class);

        detailViewModel.getMyItemInfoLiveData().observe(getViewLifecycleOwner(), string -> {
            itemInfo = string;
            String toDisplay = "Welcome to : " + itemInfo;
            binding.itemFragmentTextviewTitle.setText(toDisplay);
        });

        detailViewModel.getMyItemPhotoLiveData().observe(getViewLifecycleOwner(), string -> {
            itemPhoto = string;
            if (itemPhoto.equals("1")) {
                Glide.with(binding.itemFragmentImageviewPhoto)
                        .load("https://cdn.pixabay.com/photo/2017/08/30/01/05/milky-way-2695569_960_720.jpg")
                        .apply(RequestOptions.circleCropTransform())
                        .into(binding.itemFragmentImageviewPhoto);
            } else {
                Glide.with(binding.itemFragmentImageviewPhoto)
                        .load("https://cdn.pixabay.com/photo/2016/09/05/18/54/texture-1647380_960_720.jpg")
                        .apply(RequestOptions.circleCropTransform())
                        .into(binding.itemFragmentImageviewPhoto);
            }
        });

        return root;
    }
}
