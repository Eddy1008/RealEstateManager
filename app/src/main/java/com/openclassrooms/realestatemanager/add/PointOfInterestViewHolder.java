package com.openclassrooms.realestatemanager.add;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.PointOfInterestNearby;

public class PointOfInterestViewHolder extends RecyclerView.ViewHolder {

    private final TextView pointOfInterestName;
    private final ImageView deleteImageView;

    private final PointOfInterestAdapter.DeletePointOfInterestListener deletePointOfInterestListener;

    public PointOfInterestViewHolder(@NonNull View itemView, PointOfInterestAdapter.DeletePointOfInterestListener deletePointOfInterestListener) {
        super(itemView);
        this.deletePointOfInterestListener = deletePointOfInterestListener;
        this.pointOfInterestName = itemView.findViewById(R.id.item_point_of_interest_to_add_textview);
        this.deleteImageView = itemView.findViewById(R.id.item_point_of_interest_to_add_imageview_delete);

        deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Object tag = view.getTag();
                if (tag instanceof PointOfInterestNearby) {
                    PointOfInterestViewHolder.this.deletePointOfInterestListener.onDeletePointOfInterest((PointOfInterestNearby) tag);
                }
            }
        });
    }

    public void bind(PointOfInterestNearby pointOfInterestNearby) {
        pointOfInterestName.setText(pointOfInterestNearby.getName());
        deleteImageView.setTag(pointOfInterestNearby);
    }

}
