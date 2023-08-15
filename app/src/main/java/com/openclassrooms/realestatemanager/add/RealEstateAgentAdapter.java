package com.openclassrooms.realestatemanager.add;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.RealEstateAgent;

import java.util.List;

public class RealEstateAgentAdapter extends ArrayAdapter<RealEstateAgent> {

    private LayoutInflater layoutInflater;

    public RealEstateAgentAdapter(Context context, List<RealEstateAgent> realEstateAgents) {
        super(context, 0, realEstateAgents);
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_spinner_real_estate_agent, parent, false);
        }
        RealEstateAgent realEstateAgent = getItem(position);
        TextView textView = convertView.findViewById(R.id.item_spinner_real_estate_agent_name);

        if (realEstateAgent != null) {
            textView.setText(realEstateAgent.getName());
        }
        return convertView;
    }
}
