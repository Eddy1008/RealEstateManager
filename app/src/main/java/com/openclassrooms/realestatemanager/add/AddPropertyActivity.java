package com.openclassrooms.realestatemanager.add;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ViewModelFactory;
import com.openclassrooms.realestatemanager.databinding.ActivityAddPropertyBinding;
import com.openclassrooms.realestatemanager.model.PointOfInterestNearby;
import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.model.PropertyType;
import com.openclassrooms.realestatemanager.model.RealEstateAgent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AddPropertyActivity extends AppCompatActivity implements PointOfInterestAdapter.DeletePointOfInterestListener {

    private ActivityAddPropertyBinding binding;

    private AddPropertyViewModel addPropertyViewModel;

    private RecyclerView recyclerView;
    private PointOfInterestAdapter adapter;

    private List<PropertyType> propertyTypeList;
    private PropertyType propertyType;
    private List<RealEstateAgent> realEstateAgentList;
    private RealEstateAgent realEstateAgent;
    private List<PointOfInterestNearby> pointOfInterestList = new ArrayList<>();

    /**
     * Dialog to create a new point of interest
     */
    @Nullable
    public AlertDialog dialog = null;

    /**
     * EditText that allows user to set the name of a point of interest
     */
    @Nullable
    private EditText dialogEditTextName = null;

    /**
     * EditText that allows user to set the address of a point of interest
     */
    @Nullable
    private EditText dialogEditTextAddress = null;

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
        configurePointOfInterestRecyclerView();
        setAddPictureButton();
        setAddPointOfInterestButton();
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

    private void configurePointOfInterestRecyclerView() {
        recyclerView = binding.activityAddPropertyPointOfInterestRecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new PointOfInterestAdapter(pointOfInterestList, this);
        recyclerView.setAdapter(adapter);
    }

    private void setAddPictureButton() {
        binding.activityAddPropertyAddPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO allow to add picture
                Toast.makeText(AddPropertyActivity.this, "Will allow to add picture", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAddPointOfInterestButton() {
        binding.activityAddPropertyAddPointOfInterestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO allow to add point of interest
                showAddPointOfInterestDialog();
            }
        });
    }

    private void showAddPointOfInterestDialog() {
        final AlertDialog dialog = getAddPointOfInterestDialog();
        dialog.show();
        dialogEditTextName = dialog.findViewById(R.id.dialog_edittext_name);
        dialogEditTextAddress = dialog.findViewById(R.id.dialog_edittext_address);
    }

    private AlertDialog getAddPointOfInterestDialog() {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, R.style.Dialog);

        alertBuilder.setTitle(getString(R.string.dialog_title));
        alertBuilder.setView(R.layout.dialog_add_point_of_interest);
        alertBuilder.setPositiveButton(getString(R.string.dialog_add_button), null);
        alertBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialogEditTextName = null;
                dialogEditTextAddress = null;
                dialog = null;
            }
        });

        dialog = alertBuilder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onPositiveButtonClick(dialog);
                    }
                });
            }
        });

        return dialog;
    }

    private void onPositiveButtonClick(DialogInterface dialogInterface) {
        if (dialogEditTextName != null && dialogEditTextAddress != null) {
            String pointOfInterestName = dialogEditTextName.getText().toString();
            String pointOfInterestAddress = dialogEditTextAddress.getText().toString();

            if (pointOfInterestName.trim().isEmpty()) {
                dialogEditTextName.setError(getString(R.string.dialog_point_of_interest_name_error));
            } else if (pointOfInterestAddress.trim().isEmpty()) {
                dialogEditTextAddress.setError(getString(R.string.dialog_point_of_interest_address_error));
            } else {
                PointOfInterestNearby newPointOfInterest = new PointOfInterestNearby(pointOfInterestName, pointOfInterestAddress);
                addPointOfInterest(newPointOfInterest);
                dialogInterface.dismiss();
            }
        } else {
            dialogInterface.dismiss();
        }
    }

    private void addPointOfInterest(PointOfInterestNearby pointOfInterestNearby) {
        pointOfInterestList.add(pointOfInterestNearby);
        adapter.updatePointOfInterestList(pointOfInterestList);
        Log.d("TAG", "addPointOfInterest: pointOfInterestList.size() = " + pointOfInterestList.size());
    }

    @Override
    public void onDeletePointOfInterest(PointOfInterestNearby pointOfInterest) {
        pointOfInterestList.remove(pointOfInterest);
        adapter.updatePointOfInterestList(pointOfInterestList);
        Log.d("TAG", "onDeletePointOfInterest: pointOfInterestList.size() = " + pointOfInterestList.size());
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

                    addPropertyViewModel.insertPropertyAndGetId(property).observe(AddPropertyActivity.this, new Observer<Long>() {
                        @Override
                        public void onChanged(Long insertedId) {
                            for (PointOfInterestNearby point : pointOfInterestList) {
                                PointOfInterestNearby newPoint = new PointOfInterestNearby(point.getName(), point.getAddress(), insertedId);
                                addPropertyViewModel.addPointOfInterest(newPoint);
                            }
                            finish();
                        }
                    });
                }
            }
        });
    }

    private String getOnSaleDate() {
        String datePattern = "dd/MM/yyyy HH:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(datePattern, Locale.getDefault());
        Date now = Calendar.getInstance().getTime();
        return dateFormat.format(now);
    }
}