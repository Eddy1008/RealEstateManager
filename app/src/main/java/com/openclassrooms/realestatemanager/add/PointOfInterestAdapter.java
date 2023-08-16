package com.openclassrooms.realestatemanager.add;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.PointOfInterestNearby;

import java.util.List;

public class PointOfInterestAdapter extends RecyclerView.Adapter<PointOfInterestViewHolder> {

    private List<PointOfInterestNearby> pointOfInterestList;

    @NonNull
    private final DeletePointOfInterestListener deletePointOfInterestListener;

    public PointOfInterestAdapter(List<PointOfInterestNearby> pointOfInterestList, DeletePointOfInterestListener deletePointOfInterestListener) {
        this.pointOfInterestList = pointOfInterestList;
        this.deletePointOfInterestListener = deletePointOfInterestListener;
    }

    @NonNull
    @Override
    public PointOfInterestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_point_of_interest_to_add, parent, false);
        return new PointOfInterestViewHolder(view, deletePointOfInterestListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PointOfInterestViewHolder holder, int position) {
        holder.bind(pointOfInterestList.get(position));
    }

    @Override
    public int getItemCount() {
        return pointOfInterestList.size();
    }

    public void updatePointOfInterestList(List<PointOfInterestNearby> newList) {
        this.pointOfInterestList = newList;
        this.notifyDataSetChanged();
    }

    /**
     * Listener for deleting point Of Interest
     */
    public interface DeletePointOfInterestListener {
        /**
         * Called when a point Of Interest needs to be deleted.
         *
         * @param pointOfInterest the point Of Interest that needs to be deleted
         */
        void onDeletePointOfInterest(PointOfInterestNearby pointOfInterest);
    }
}
