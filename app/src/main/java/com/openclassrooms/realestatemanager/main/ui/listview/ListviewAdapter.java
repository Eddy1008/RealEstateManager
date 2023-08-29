package com.openclassrooms.realestatemanager.main.ui.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.model.PropertyType;

import java.util.List;

public class ListviewAdapter extends RecyclerView.Adapter<ListviewItemViewHolder> {

    private List<Property> propertyList;
    private List<PropertyType> propertyTypeList;
    private boolean isTwoPane;

    public ListviewAdapter(List<Property> propertyList, List<PropertyType> propertyTypeList, boolean isTwoPaneMode) {
        this.propertyList = propertyList;
        this.propertyTypeList = propertyTypeList;
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
        holder.bind(propertyList.get(position), propertyTypeList, isTwoPane);
    }

    @Override
    public int getItemCount() {
        return propertyList.size();
    }

    public void updateMyList(List<Property> newList) {
        this.propertyList = newList;
        this.notifyDataSetChanged();
    }

    public void updatePropertyTypeList(List<PropertyType> newPropertyTypeList) {
        this.propertyTypeList = newPropertyTypeList;
        this.notifyDataSetChanged();
    }
}
