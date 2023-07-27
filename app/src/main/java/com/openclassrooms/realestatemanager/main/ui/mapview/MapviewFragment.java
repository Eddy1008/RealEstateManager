package com.openclassrooms.realestatemanager.main.ui.mapview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.openclassrooms.realestatemanager.databinding.FragmentMapviewBinding;

public class MapviewFragment extends Fragment {

    private FragmentMapviewBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentMapviewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.mapviewFragmentTextviewTitle.setText("Welcome to my MapView");

        return root;
    }
}
