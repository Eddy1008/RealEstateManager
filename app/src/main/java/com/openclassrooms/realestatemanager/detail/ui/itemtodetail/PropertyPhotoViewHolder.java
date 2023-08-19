package com.openclassrooms.realestatemanager.detail.ui.itemtodetail;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.PropertyPhoto;

public class PropertyPhotoViewHolder extends RecyclerView.ViewHolder {

    private final ImageView picture;
    private final TextView description;

    public PropertyPhotoViewHolder(@NonNull View itemView) {
        super(itemView);
        this.picture = itemView.findViewById(R.id.item_property_photo_picture);
        this.description = itemView.findViewById(R.id.item_property_photo_description);
    }

    public void bind(PropertyPhoto propertyPhoto) {
        Glide.with(this.picture.getContext())
                .load(propertyPhoto.getPhotoUrl())
                .centerCrop()
                .into(picture);
        description.setText(propertyPhoto.getPhotoDescription());
    }
}
