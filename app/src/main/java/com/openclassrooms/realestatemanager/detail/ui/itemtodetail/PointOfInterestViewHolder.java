package com.openclassrooms.realestatemanager.detail.ui.itemtodetail;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.PointOfInterestNearby;

public class PointOfInterestViewHolder extends RecyclerView.ViewHolder {

    private final TextView name;
    private final TextView address;
    private final TextView markerChar;

    public PointOfInterestViewHolder(@NonNull View itemView) {
        super(itemView);
        this.name = itemView.findViewById(R.id.item_point_of_interest_name);
        this.address = itemView.findViewById(R.id.item_point_of_interest_address);
        this.markerChar = itemView.findViewById(R.id.item_point_of_interest_marker_char);
    }

    public void bind(PointOfInterestNearby pointOfInterestNearby, char markerId) {
        name.setText(pointOfInterestNearby.getName());
        address.setText(pointOfInterestNearby.getAddress());
        markerChar.setText(String.valueOf(markerId));
    }
}
