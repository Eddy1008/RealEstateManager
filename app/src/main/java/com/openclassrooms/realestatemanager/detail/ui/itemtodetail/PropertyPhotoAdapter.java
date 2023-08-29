package com.openclassrooms.realestatemanager.detail.ui.itemtodetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.PropertyPhoto;

import java.util.List;

public class PropertyPhotoAdapter extends RecyclerView.Adapter<PropertyPhotoViewHolder> {

    private final List<PropertyPhoto> propertyPhotoList;

    public PropertyPhotoAdapter(List<PropertyPhoto> propertyPhotoList) {
        this.propertyPhotoList = propertyPhotoList;
    }

    @NonNull
    @Override
    public PropertyPhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_property_photo, parent, false);
        return new PropertyPhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyPhotoViewHolder holder, int position) {
        holder.bind(propertyPhotoList.get(position));
    }

    @Override
    public int getItemCount() {
        return propertyPhotoList.size();
    }
}
