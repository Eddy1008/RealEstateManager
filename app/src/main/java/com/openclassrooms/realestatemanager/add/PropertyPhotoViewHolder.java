package com.openclassrooms.realestatemanager.add;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.customview.DeleteButtonImageView;
import com.openclassrooms.realestatemanager.model.PropertyPhoto;

public class PropertyPhotoViewHolder extends RecyclerView.ViewHolder {

    private DeleteButtonImageView deletePicture;
    private ImageView picture;

    private final PropertyPhotoAdapter.DeletePropertyPhotoListener deletePropertyPhotoListener;

    public PropertyPhotoViewHolder(@NonNull View itemView, PropertyPhotoAdapter.DeletePropertyPhotoListener deletePropertyPhotoListener) {
        super(itemView);
        this.deletePropertyPhotoListener = deletePropertyPhotoListener;
        this.picture = itemView.findViewById(R.id.item_property_photo_to_add_imageview);
        this.deletePicture = itemView.findViewById(R.id.item_property_photo_to_add_delete_button);

        deletePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Object tag = view.getTag();
                if (tag instanceof PropertyPhoto) {
                    PropertyPhotoViewHolder.this.deletePropertyPhotoListener.onDeletePropertyPhoto((PropertyPhoto) tag);
                }
            }
        });
    }

    public void bind(PropertyPhoto propertyPhoto) {
        Glide.with(this.picture.getContext())
                .load(propertyPhoto.getPhotoUrl())
                .centerCrop()
                .into(picture);
        deletePicture.setTag(propertyPhoto);
    }
}
