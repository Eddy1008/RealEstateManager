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

import com.openclassrooms.realestatemanager.ViewModelFactory;
import com.openclassrooms.realestatemanager.databinding.FragmentItemToDetailBinding;
import com.openclassrooms.realestatemanager.detail.DetailViewModel;

public class ItemFragment extends Fragment {

    private FragmentItemToDetailBinding binding;

    private DetailViewModel detailViewModel;

    private String userName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentItemToDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        detailViewModel = new ViewModelProvider(requireActivity(), ViewModelFactory.getInstance()).get(DetailViewModel.class);

        detailViewModel.getMyUserNameLiveData().observe(getViewLifecycleOwner(), string -> {
            userName = string;
            String toDisplay = "Welcome to my ItemView, " + userName;
            binding.itemFragmentTextviewTitle.setText(toDisplay);
        });

        return root;
    }
}
