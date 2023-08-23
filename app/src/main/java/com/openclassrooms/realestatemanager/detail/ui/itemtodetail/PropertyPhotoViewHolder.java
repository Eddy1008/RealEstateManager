package com.openclassrooms.realestatemanager.detail.ui.itemtodetail;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.PropertyPhoto;

public class PropertyPhotoViewHolder extends RecyclerView.ViewHolder {

    @Nullable
    public AlertDialog propertyPhotoDialog = null;

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

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageDialog(propertyPhoto.getPhotoUrl());
            }
        });
    }

    public void showImageDialog(String photoToDisplay) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(itemView.getContext());

        View dialogView = LayoutInflater.from(itemView.getContext()).inflate(R.layout.dialog_zoom_picture, null);
        ImageView dialogZoomPicture = dialogView.findViewById(R.id.dialog_zoom_picture_imageview);

        Glide.with(dialogZoomPicture.getContext())
                .load(photoToDisplay)
                .fitCenter()
                .into(dialogZoomPicture);

        dialogZoomPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                propertyPhotoDialog.dismiss();
            }
        });

        alertBuilder.setTitle(null);
        alertBuilder.setView(dialogView);

        propertyPhotoDialog = alertBuilder.create();
        propertyPhotoDialog.show();
    }
}
