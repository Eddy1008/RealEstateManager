package com.openclassrooms.realestatemanager.add;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ViewModelFactory;
import com.openclassrooms.realestatemanager.databinding.ActivityAddPropertyBinding;
import com.openclassrooms.realestatemanager.model.PointOfInterestNearby;
import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.model.PropertyPhoto;
import com.openclassrooms.realestatemanager.model.PropertyType;
import com.openclassrooms.realestatemanager.model.RealEstateAgent;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddPropertyActivity extends AppCompatActivity implements PointOfInterestAdapter.DeletePointOfInterestListener, PropertyPhotoAdapter.DeletePropertyPhotoListener {

    private ActivityAddPropertyBinding binding;

    private AddPropertyViewModel addPropertyViewModel;

    private PointOfInterestAdapter adapter;
    private PropertyPhotoAdapter propertyPhotoAdapter;

    private List<PropertyType> propertyTypeList;
    private PropertyType propertyType;
    private List<RealEstateAgent> realEstateAgentList;
    private RealEstateAgent realEstateAgent;
    private List<PointOfInterestNearby> pointOfInterestList = new ArrayList<>();
    private List<PropertyPhoto> propertyPhotoList = new ArrayList<>();

    public static final int CAMERA_ACTION_CODE = 12;
    public static final int PICK_ACTION_CODE = 13;
    private static final int REQUEST_CAMERA_PERMISSION = 101;

    /**
     * Dialog to create a new property photo
     */
    @Nullable
    public AlertDialog propertyPhotoDialog = null;
    @Nullable
    private Button dialogPropertyPhotoTakePictureButton = null;
    @Nullable
    private Button dialogPropertyPhotoSelectPictureButton = null;
    @Nullable
    private EditText dialogPropertyPhotoEditTextDescription = null;
    @Nullable
    private ImageView dialogPropertyPhotoImageViewPicture = null;
    @Nullable
    private PropertyPhoto pictureToAdd = null;

    /**
     * Dialog to create a new point of interest
     */
    @Nullable
    public AlertDialog pointOfInterestDialog = null;
    @Nullable
    private EditText dialogPointOfInterestEditTextName = null;
    @Nullable
    private EditText dialogPointOfInterestEditTextAddress = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddPropertyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addPropertyViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(this)).get(AddPropertyViewModel.class);
        addPropertyViewModel.initPropertyTypeList();
        addPropertyViewModel.initRealEstateAgentList();

        configurePropertyPhotoRecyclerView();
        configurePropertyTypeSpinner();
        configureRealEstateAgentSpinner();
        configurePointOfInterestRecyclerView();

        setPreviousPageButton();
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

    // *****************************
    // ******* PROPERTY TYPE *******
    // *****************************

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

    // *********************************
    // ******* REAL ESTATE AGENT *******
    // *********************************

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

    // ******************************
    // ******* PROPERTY PHOTO *******
    // ******************************

    private void configurePropertyPhotoRecyclerView() {
        RecyclerView pictureRecyclerView = binding.activityAddPropertyPictureRecyclerview;
        pictureRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        propertyPhotoAdapter = new PropertyPhotoAdapter(propertyPhotoList, this);
        addPropertyViewModel.getListOfPropertyPhotoToAdd().observe(this, propertyPhotoList1 -> {
            propertyPhotoList = propertyPhotoList1;
            propertyPhotoAdapter.updatePropertyPhotoList(propertyPhotoList);
            pictureRecyclerView.setAdapter(propertyPhotoAdapter);
        });
    }

    private void setAddPictureButton() {
        binding.activityAddPropertyAddPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddPropertyPhotoDialog();
            }
        });
    }

    private void showAddPropertyPhotoDialog() {
        final AlertDialog photoDialog = getAddPropertyPhotoDialog();
        photoDialog.show();
        dialogPropertyPhotoImageViewPicture = photoDialog.findViewById(R.id.dialog_property_photo_picture);
        dialogPropertyPhotoTakePictureButton = photoDialog.findViewById(R.id.dialog_property_photo_take_button);
        dialogPropertyPhotoSelectPictureButton = photoDialog.findViewById(R.id.dialog_property_photo_select_button);
        dialogPropertyPhotoEditTextDescription = photoDialog.findViewById(R.id.dialog_property_photo_description_edittext);

        dialogPropertyPhotoTakePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkCameraPermission()) {
                    takePictureIntent();
                }
            }
        });

        dialogPropertyPhotoSelectPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPictureIntent();
            }
        });
    }

    private AlertDialog getAddPropertyPhotoDialog() {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, R.style.Dialog);

        alertBuilder.setTitle(getString(R.string.dialog_photo_title));
        alertBuilder.setView(R.layout.dialog_add_property_photo);
        alertBuilder.setPositiveButton(getString(R.string.dialog_add_picture_button), null);
        alertBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialogPropertyPhotoImageViewPicture = null;
                dialogPropertyPhotoTakePictureButton = null;
                dialogPropertyPhotoSelectPictureButton = null;
                dialogPropertyPhotoEditTextDescription = null;
                propertyPhotoDialog = null;
                pictureToAdd = null;
            }
        });

        propertyPhotoDialog = alertBuilder.create();

        propertyPhotoDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = propertyPhotoDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onPropertyPhotoPositiveButtonClick(propertyPhotoDialog);
                    }
                });
            }
        });

        return propertyPhotoDialog;
    }

    private void onPropertyPhotoPositiveButtonClick(DialogInterface dialogInterface) {
        if (dialogPropertyPhotoEditTextDescription != null) {
            String propertyPhotoDescription = dialogPropertyPhotoEditTextDescription.getText().toString();
            if (propertyPhotoDescription.trim().isEmpty()) {
                dialogPropertyPhotoEditTextDescription.setError(getString(R.string.dialog_property_photo_description_error));
            } else if (pictureToAdd == null) {
                Toast.makeText(this, getString(R.string.add_property_no_choice_photo), Toast.LENGTH_SHORT).show();
            } else {
                pictureToAdd.setPhotoDescription(dialogPropertyPhotoEditTextDescription.getText().toString());
                addPropertyPhoto(pictureToAdd);
                dialogInterface.dismiss();
            }
        } else {
            dialogInterface.dismiss();
        }
    }

    private boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePictureIntent();
            } else {
                Toast.makeText(this, getString(R.string.add_property_allow_app_take_photo), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void takePictureIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAMERA_ACTION_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_ACTION_CODE && resultCode == RESULT_OK && data != null) {
            Bundle bundle = data.getExtras();
            Bitmap imageToSave = (Bitmap) bundle.get("data");

            if (dialogPropertyPhotoImageViewPicture != null) {
                dialogPropertyPhotoImageViewPicture.setImageBitmap(imageToSave);
            }

            String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
            String fileName = "REM_picture_" + timeStamp + ".jpg";
            createDirectoryAndSaveFile(imageToSave,fileName);
        }

        if (requestCode == PICK_ACTION_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            if (dialogPropertyPhotoImageViewPicture != null) {
                dialogPropertyPhotoImageViewPicture.setImageURI(selectedImageUri);
            }
            String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
            String fileName = "REM_picture_" + timeStamp + ".jpg";
            pictureToAdd = new PropertyPhoto(selectedImageUri.toString(), fileName);

        }
    }

    private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File pictureFile = new File(directory, fileName);
        try {
            FileOutputStream outputStream = new FileOutputStream(pictureFile);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            Log.e("TAG", "createDirectoryAndSaveFile: " + e.getMessage(), e );
        }
        pictureToAdd = new PropertyPhoto(pictureFile.getAbsolutePath(), fileName);
    }

    private void selectPictureIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_ACTION_CODE);
    }

    private void addPropertyPhoto(PropertyPhoto propertyPhoto) {
        addPropertyViewModel.addPropertyPhotoToAddList(propertyPhoto);
    }

    @Override
    public void onDeletePropertyPhoto(PropertyPhoto propertyPhoto) {
        addPropertyViewModel.deletePropertyPhotoFromAddList(propertyPhoto);
    }

    // *********************************
    // ******* POINT OF INTEREST *******
    // *********************************

    private void configurePointOfInterestRecyclerView() {
        RecyclerView recyclerView = binding.activityAddPropertyPointOfInterestRecyclerview;
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new PointOfInterestAdapter(pointOfInterestList, this);
        addPropertyViewModel.getListOfPointOfInterestToAdd().observe(this, pointOfInterestNearbies -> {
            pointOfInterestList = pointOfInterestNearbies;
            adapter.updatePointOfInterestList(pointOfInterestList);
            recyclerView.setAdapter(adapter);
        });

    }

    private void setAddPointOfInterestButton() {
        binding.activityAddPropertyAddPointOfInterestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddPointOfInterestDialog();
            }
        });
    }

    private void showAddPointOfInterestDialog() {
        final AlertDialog dialog = getAddPointOfInterestDialog();
        dialog.show();
        dialogPointOfInterestEditTextName = dialog.findViewById(R.id.dialog_edittext_name);
        dialogPointOfInterestEditTextAddress = dialog.findViewById(R.id.dialog_edittext_address);
    }

    private AlertDialog getAddPointOfInterestDialog() {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, R.style.Dialog);

        alertBuilder.setTitle(getString(R.string.dialog_title));
        alertBuilder.setView(R.layout.dialog_add_point_of_interest);
        alertBuilder.setPositiveButton(getString(R.string.dialog_add_button), null);
        alertBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialogPointOfInterestEditTextName = null;
                dialogPointOfInterestEditTextAddress = null;
                pointOfInterestDialog = null;
            }
        });

        pointOfInterestDialog = alertBuilder.create();

        pointOfInterestDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = pointOfInterestDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onPointOfInterestPositiveButtonClick(pointOfInterestDialog);
                    }
                });
            }
        });

        return pointOfInterestDialog;
    }

    private void onPointOfInterestPositiveButtonClick(DialogInterface dialogInterface) {
        if (dialogPointOfInterestEditTextName != null && dialogPointOfInterestEditTextAddress != null) {
            String pointOfInterestName = dialogPointOfInterestEditTextName.getText().toString();
            String pointOfInterestAddress = dialogPointOfInterestEditTextAddress.getText().toString();

            if (pointOfInterestName.trim().isEmpty()) {
                dialogPointOfInterestEditTextName.setError(getString(R.string.dialog_point_of_interest_name_error));
            } else if (pointOfInterestAddress.trim().isEmpty()) {
                dialogPointOfInterestEditTextAddress.setError(getString(R.string.dialog_point_of_interest_address_error));
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
        addPropertyViewModel.addPointOfInterestToAddList(pointOfInterestNearby);
    }

    @Override
    public void onDeletePointOfInterest(PointOfInterestNearby pointOfInterest) {
        addPropertyViewModel.deletePointOfInterestToAddList(pointOfInterest);
    }

    // ************************
    // ******* PROPERTY *******
    // ************************

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
                } else if (propertyPhotoList.size() == 0) {
                    Toast.makeText(AddPropertyActivity.this, getString(R.string.add_property_add_photo), Toast.LENGTH_SHORT).show();
                } else {
                    // Create a property
                    Property property = new Property(
                            binding.activityAddPropertyEdittextTitle.getText().toString(),
                            binding.activityAddPropertyEdittextAddress.getText().toString(),
                            propertyPhotoList.get(0).getPhotoUrl(),
                            binding.activityAddPropertyEditTextMultilineDescription.getText().toString(),
                            getDate(),
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
                            for (PropertyPhoto photo : propertyPhotoList) {
                                PropertyPhoto newPhoto = new PropertyPhoto(photo.getPhotoUrl(), photo.getPhotoDescription(), insertedId);
                                addPropertyViewModel.addPropertyPhoto(newPhoto);
                            }
                            Toast.makeText(AddPropertyActivity.this, getString(R.string.add_property_done) + insertedId , Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
            }
        });
    }

    private String getDate() {
        String datePattern = "dd/MM/yyyy HH:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(datePattern, Locale.getDefault());
        Date now = Calendar.getInstance().getTime();
        return dateFormat.format(now);
    }
}