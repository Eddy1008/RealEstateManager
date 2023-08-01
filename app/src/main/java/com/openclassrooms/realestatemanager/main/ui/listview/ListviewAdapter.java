package com.openclassrooms.realestatemanager.main.ui.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.MyItemTest;

import java.util.List;

public class ListviewAdapter extends RecyclerView.Adapter<ListviewItemViewHolder> {

    private List<MyItemTest> myItemTestList;
    private boolean isTwoPane;

    public ListviewAdapter(List<MyItemTest> myItemTestList, boolean isTwoPaneMode) {
        this.myItemTestList = myItemTestList;
        this.isTwoPane = isTwoPaneMode;
    }

    @NonNull
    @Override
    public ListviewItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_listview, parent, false);
        return new ListviewItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListviewItemViewHolder holder, int position) {
        holder.bind(myItemTestList.get(position), isTwoPane);
    }

    @Override
    public int getItemCount() {
        return myItemTestList.size();
    }

    public void updateMyList(List<MyItemTest> newList) {
        this.myItemTestList = newList;
        this.notifyDataSetChanged();
    }
}
