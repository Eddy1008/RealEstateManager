package com.openclassrooms.realestatemanager.add;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ViewModelFactory;
import com.openclassrooms.realestatemanager.databinding.ActivityAddPropertyBinding;
import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.model.PropertyType;
import com.openclassrooms.realestatemanager.model.RealEstateAgent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddPropertyActivity extends AppCompatActivity {

    private ActivityAddPropertyBinding binding;

    private AddPropertyViewModel addPropertyViewModel;
    private List<PropertyType> propertyTypeList;
    private List<RealEstateAgent> realEstateAgentList;

    private PropertyType propertyType;
    private RealEstateAgent realEstateAgent;
    private List<Property> maListe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddPropertyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addPropertyViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(this)).get(AddPropertyViewModel.class);
        addPropertyViewModel.initPropertyTypeList();
        addPropertyViewModel.initRealEstateAgentList();

        setPreviousPageButton();
        configurePropertyTypeSpinner();
        configureRealEstateAgentSpinner();
        setAddPropertyButton();
    }

    void setPreviousPageButton() {
        binding.activityAddPropertyPreviousPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void configurePropertyTypeSpinner() {
        addPropertyViewModel.getPropertyTypeList().observe(this, propertyTypes -> {
            propertyTypeList = propertyTypes;

            PropertyTypeAdapter propertyTypeAdapter = new PropertyTypeAdapter(this, propertyTypeList);
            binding.activityAddPropertySpinnerPropertyType.setAdapter(propertyTypeAdapter);

            propertyType = propertyTypeList.get(0);
            binding.activityAddPropertySpinnerPropertyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    propertyType = (PropertyType) adapterView.getItemAtPosition(i);
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {}
            });
        });
    }

    private void configureRealEstateAgentSpinner() {
        addPropertyViewModel.getRealEstateAgentList().observe(this, realEstateAgents -> {
            realEstateAgentList = realEstateAgents;

            RealEstateAgentAdapter realEstateAgentAdapter = new RealEstateAgentAdapter(this, realEstateAgentList);
            binding.activityAddPropertySpinnerRealEstateAgent.setAdapter(realEstateAgentAdapter);

            realEstateAgent = realEstateAgentList.get(0);
            binding.activityAddPropertySpinnerRealEstateAgent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    realEstateAgent = (RealEstateAgent) adapterView.getItemAtPosition(i);
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {}
            });
        });
    }

    private void setAddPropertyButton() {
        binding.activityAddPropertyAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.activityAddPropertyEdittextTitle.getText().toString().equals("")) {
                    binding.activityAddPropertyEdittextTitle.setError(getString(R.string.add_property_title_error));
                } else if (binding.activityAddPropertyEdittextAddress.getText().toString().equals("")) {
                    binding.activityAddPropertyEdittextAddress.setError(getString(R.string.add_property_address_error));
                } else if (binding.activityAddPropertyEditTextMultilineDescription.getText().toString().equals("")) {
                    binding.activityAddPropertyEditTextMultilineDescription.setError(getString(R.string.add_property_description_error));
                } else if (binding.activityAddPropertyEdittextSurface.getText().toString().equals("")) {
                    binding.activityAddPropertyEdittextSurface.setError(getString(R.string.add_property_surface_error));
                } else if (binding.activityAddPropertyEdittextRoomNumber.getText().toString().equals("")) {
                    binding.activityAddPropertyEdittextRoomNumber.setError(getString(R.string.add_property_room_error));
                } else if (binding.activityAddPropertyEdittextBathroomNumber.getText().toString().equals("")) {
                    binding.activityAddPropertyEdittextBathroomNumber.setError(getString(R.string.add_property_bathroom_error));
                } else if (binding.activityAddPropertyEdittextBedroomNumber.getText().toString().equals("")) {
                    binding.activityAddPropertyEdittextBedroomNumber.setError(getString(R.string.add_property_bedroom_error));
                } else if (binding.activityAddPropertyEdittextPrice.getText().toString().equals("")) {
                    binding.activityAddPropertyEdittextPrice.setError(getString(R.string.add_property_price_error));
                } else {
                    // Create a property
                    Property property = new Property(
                            binding.activityAddPropertyEdittextTitle.getText().toString(),
                            binding.activityAddPropertyEdittextAddress.getText().toString(),
                            "https://cdn.pixabay.com/photo/2017/08/30/01/05/milky-way-2695569_960_720.jpg",
                            binding.activityAddPropertyEditTextMultilineDescription.getText().toString(),
                            getOnSaleDate(),
                            "",
                            Integer.parseInt(binding.activityAddPropertyEdittextPrice.getText().toString()),
                            Integer.parseInt(binding.activityAddPropertyEdittextSurface.getText().toString()),
                            Integer.parseInt(binding.activityAddPropertyEdittextRoomNumber.getText().toString()),
                            Integer.parseInt(binding.activityAddPropertyEdittextBathroomNumber.getText().toString()),
                            Integer.parseInt(binding.activityAddPropertyEdittextBedroomNumber.getText().toString()),
                            propertyType.getId(),
                            1,
                            realEstateAgent.getId()
                            );

                    addProperty(property);
                    finish();
                }
            }
        });
    }

    private void addProperty(Property property) {
        addPropertyViewModel.addProperty(property);
    }

    private String getOnSaleDate() {
        String datePattern = "dd/MM/yyyy HH:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(datePattern, Locale.getDefault());
        Date now = Calendar.getInstance().getTime();
        return dateFormat.format(now);
    }
}