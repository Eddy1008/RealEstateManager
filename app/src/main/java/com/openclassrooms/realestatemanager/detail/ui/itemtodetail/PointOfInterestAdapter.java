package com.openclassrooms.realestatemanager.detail.ui.itemtodetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.PointOfInterestNearby;

import java.util.List;

public class PointOfInterestAdapter extends RecyclerView.Adapter<PointOfInterestViewHolder> {

    private final List<PointOfInterestNearby> pointOfInterestList;

    public PointOfInterestAdapter(List<PointOfInterestNearby> pointOfInterestList) {
        this.pointOfInterestList = pointOfInterestList;
    }

    @NonNull
    @Override
    public PointOfInterestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_point_of_interest, parent, false);
        return new PointOfInterestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PointOfInterestViewHolder holder, int position) {
        int alphabetInt = 65 + position;
        char markerId = (char) alphabetInt;
        holder.bind(pointOfInterestList.get(position), markerId);
    }

    @Override
    public int getItemCount() {
        return pointOfInterestList.size();
    }
}
