package com.openclassrooms.realestatemanager.add;

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

    private List<PropertyPhoto> propertyPhotoList;

    @NonNull
    private final DeletePropertyPhotoListener deletePropertyPhotoListener;

    public PropertyPhotoAdapter(List<PropertyPhoto> propertyPhotoList, @NonNull DeletePropertyPhotoListener deletePropertyPhotoListener) {
        this.propertyPhotoList = propertyPhotoList;
        this.deletePropertyPhotoListener = deletePropertyPhotoListener;
    }

    @NonNull
    @Override
    public PropertyPhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_property_photo_to_add, parent, false);
        return new PropertyPhotoViewHolder(view, deletePropertyPhotoListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyPhotoViewHolder holder, int position) {
        holder.bind(propertyPhotoList.get(position));
    }

    @Override
    public int getItemCount() {
        return propertyPhotoList.size();
    }

    public void updatePropertyPhotoList(List<PropertyPhoto> newList) {
        this.propertyPhotoList = newList;
        this.notifyDataSetChanged();
    }

    /**
     * Listener for deleting property Photo
     */
    public interface DeletePropertyPhotoListener{
        /**
         * Called when a property photo needs to be deleted.
         *
         * @param propertyPhoto the propertyPhoto that needs to be deleted
         */
        void onDeletePropertyPhoto(PropertyPhoto propertyPhoto);
    }
}
