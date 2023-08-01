package com.openclassrooms.realestatemanager.main.ui.listview;

import android.os.Bundle;
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

import com.openclassrooms.realestatemanager.ViewModelFactory;
import com.openclassrooms.realestatemanager.databinding.FragmentListviewBinding;
import com.openclassrooms.realestatemanager.main.MainViewModel;
import com.openclassrooms.realestatemanager.model.MyItemTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListviewFragment extends Fragment {

    private FragmentListviewBinding binding;

    private RecyclerView recyclerView;
    private ListviewAdapter adapter;
    private MainViewModel mainViewModel;
    private boolean isTwoPane;
    private List<MyItemTest> myTestList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentListviewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.listviewRecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));

        mainViewModel = new ViewModelProvider(requireActivity(), ViewModelFactory.getInstance()).get(MainViewModel.class);

        mainViewModel.getIsTwoPaneMode().observe(getViewLifecycleOwner(), aBoolean -> {
            isTwoPane = aBoolean;
            adapter = new ListviewAdapter(myTestList, isTwoPane);
            getMyItems();
        });

        return root;
    }

    private void getMyItems() {
        MyItemTest test0 = new MyItemTest("A test", "1");
        MyItemTest test1 = new MyItemTest("B test", "2");
        MyItemTest test2 = new MyItemTest("C test", "1");
        MyItemTest test3 = new MyItemTest("D test", "2");
        MyItemTest test4 = new MyItemTest("E test", "1");
        MyItemTest test5 = new MyItemTest("F test", "2");
        MyItemTest test6 = new MyItemTest("G test", "1");
        MyItemTest test7 = new MyItemTest("H test", "2");
        MyItemTest test8 = new MyItemTest("I test", "1");
        MyItemTest test9 = new MyItemTest("J test", "2");

        myTestList.add(test0);
        myTestList.add(test1);
        myTestList.add(test2);
        myTestList.add(test3);
        myTestList.add(test4);
        myTestList.add(test5);
        myTestList.add(test6);
        myTestList.add(test7);
        myTestList.add(test8);
        myTestList.add(test9);

        adapter.updateMyList(myTestList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
