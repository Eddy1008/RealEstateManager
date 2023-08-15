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
import com.openclassrooms.realestatemanager.model.PropertyType;

import java.util.List;

public class PropertyTypeAdapter extends ArrayAdapter<PropertyType> {

    private LayoutInflater layoutInflater;

    public PropertyTypeAdapter(Context context, List<PropertyType> propertyTypes) {
        super(context, 0, propertyTypes);
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_spinner_property_type, parent, false);
        }
        PropertyType propertyType = getItem(position);
        TextView textView = convertView.findViewById(R.id.item_spinner_property_type_name);

        if (propertyType != null) {
            textView.setText(propertyType.getName());
        }
        return convertView;
    }
}
