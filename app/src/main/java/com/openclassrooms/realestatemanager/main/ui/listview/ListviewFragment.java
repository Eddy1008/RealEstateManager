package com.openclassrooms.realestatemanager.main.ui.listview;

import android.os.Bundle;
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

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ViewModelFactory;
import com.openclassrooms.realestatemanager.databinding.FragmentListviewBinding;
import com.openclassrooms.realestatemanager.main.MainViewModel;
import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.model.PropertyType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListviewFragment extends Fragment {

    private FragmentListviewBinding binding;

    private RecyclerView recyclerView;
    private ListviewAdapter adapter;
    private MainViewModel mainViewModel;
    private boolean isTwoPane;
    private List<Property> propertyList = new ArrayList<>();
    private List<PropertyType> propertyTypeList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentListviewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.listviewRecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(recyclerView.getContext(), R.drawable.item_listview_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        mainViewModel = new ViewModelProvider(requireActivity(), ViewModelFactory.getInstance(getContext())).get(MainViewModel.class);
        mainViewModel.initPropertyList();
        mainViewModel.initPropertyTypeList();

        mainViewModel.getIsTwoPaneMode().observe(getViewLifecycleOwner(), aBoolean -> {
            isTwoPane = aBoolean;
            adapter = new ListviewAdapter(propertyList, propertyTypeList, isTwoPane);
            getMyPropertyTypeList();
            getMyPropertyList();
        });

        return root;
    }

    private void getMyPropertyTypeList() {
        mainViewModel.getPropertyTypeList().observe(getViewLifecycleOwner(), propertyTypes -> {
            propertyTypeList = new ArrayList<>(propertyTypes);
            adapter.updatePropertyTypeList(propertyTypeList);
        });
    }

    private void getMyPropertyList() {
        mainViewModel.getPropertyList().observe(getViewLifecycleOwner(), properties -> {
            if (properties != null) {
                propertyList = new ArrayList<>(properties);
                adapter.updateMyList(propertyList);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
