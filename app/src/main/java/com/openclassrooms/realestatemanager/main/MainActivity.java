package com.openclassrooms.realestatemanager.main;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.ViewModelFactory;
import com.openclassrooms.realestatemanager.add.AddPropertyActivity;
import com.openclassrooms.realestatemanager.add.PropertyTypeAdapter;
import com.openclassrooms.realestatemanager.add.RealEstateAgentAdapter;
import com.openclassrooms.realestatemanager.contentprovider.PropertyProvider;
import com.openclassrooms.realestatemanager.databinding.ActivityMainBinding;
import com.openclassrooms.realestatemanager.model.PropertyType;
import com.openclassrooms.realestatemanager.model.RealEstateAgent;
import com.openclassrooms.realestatemanager.simulator.SimulatorActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AppBarConfiguration mAppBarConfiguration;
    private MainViewModel mainViewModel;
    private List<PropertyType> propertyTypeList;
    private PropertyType propertyType;
    private PropertyType noChoiceType;
    private List<RealEstateAgent> realEstateAgentList;
    private RealEstateAgent realEstateAgent;
    private RealEstateAgent noChoiceAgent;

    /**
     * Dialog to create a new point of interest
     */
    @Nullable
    public AlertDialog searchDialog = null;
    @Nullable
    private EditText dialogSearchEditTitle = null;
    @Nullable
    private EditText dialogSearchEditAddress = null;
    @Nullable
    private EditText dialogSearchEditSurfaceMin = null;
    @Nullable
    private EditText dialogSearchEditSurfaceMax = null;
    @Nullable
    private EditText dialogSearchEditRoom = null;
    @Nullable
    private EditText dialogSearchEditBathroom = null;
    @Nullable
    private EditText dialogSearchEditBedroom = null;
    @Nullable
    private EditText dialogSearchEditPrice = null;
    @Nullable
    private Spinner dialogSearchSpinnerType = null;
    @Nullable
    private Spinner dialogSearchSpinnerAgent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mainViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(this)).get(MainViewModel.class);
        mainViewModel.initPropertyTypeList();
        mainViewModel.initRealEstateAgentList();

        setSupportActionBar(binding.appBarMain.toolbar);

        // UI Settings
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        BottomNavigationView bottomNavigationView = binding.appBarMain.contentMain.bottomNavView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_listview, R.id.nav_mapview)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        checkIfLandscape();

        // Set Add Property Button !!
        binding.navView.getMenu().getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                Intent intent = new Intent(MainActivity.this, AddPropertyActivity.class);
                startActivity(intent);
                return false;
            }
        });

        // Set ContentProvider test button !!
        binding.navView.getMenu().getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {

                Toast.makeText(MainActivity.this, "Hello you !", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onClick: " + PropertyProvider.CONTENT_URI);
                ContentResolver contentResolver = getContentResolver();
                Cursor cursor = contentResolver.query(PropertyProvider.CONTENT_URI, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        long id = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
                        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                        String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                        String mainPhoto = cursor.getString(cursor.getColumnIndexOrThrow("mainPhoto"));
                        String propertyDescription = cursor.getString(cursor.getColumnIndexOrThrow("propertyDescription"));
                        String onSaleDate = cursor.getString(cursor.getColumnIndexOrThrow("onSaleDate"));
                        String saleDealDate = cursor.getString(cursor.getColumnIndexOrThrow("saleDealDate"));
                        int propertyPrice = cursor.getInt(cursor.getColumnIndexOrThrow("propertyPrice"));
                        int propertySurface = cursor.getInt(cursor.getColumnIndexOrThrow("propertySurface"));
                        int roomNumber = cursor.getInt(cursor.getColumnIndexOrThrow("roomNumber"));
                        int bathroomNumber = cursor.getInt(cursor.getColumnIndexOrThrow("bathroomNumber"));
                        int bedroomNumber = cursor.getInt(cursor.getColumnIndexOrThrow("bedroomNumber"));
                        long propertyTypeId = cursor.getLong(cursor.getColumnIndexOrThrow("propertyTypeId"));
                        long propertySaleStatusId = cursor.getLong(cursor.getColumnIndexOrThrow("propertySaleStatusId"));
                        long realEstateAgentId = cursor.getLong(cursor.getColumnIndexOrThrow("realEstateAgentId"));

                        Log.d("TAG", "Property - ID: " + id + ", \n Title: " + title + ", \n address: " + address + ", \n mainPhoto: " + mainPhoto +
                                ", \n propertyDescription: " + propertyDescription + ", \n onSaleDate: " + onSaleDate + ", \n saleDealDate: " + saleDealDate +
                                ", \n propertyPrice: " + propertyPrice + ", \n propertySurface: " + propertySurface + ", \n roomNumber: " + roomNumber +
                                ", \n bathroomNumber: " + bathroomNumber + ", \n bedroomNumber: " + bedroomNumber + ", \n propertyTypeId: " + propertyTypeId +
                                ", \n propertySaleStatusId: " + propertySaleStatusId + ", \n realEstateAgentId: " + realEstateAgentId);
                    } while (cursor.moveToNext());

                    cursor.close();
                } else {
                    Log.d("TAG", "No data available.");
                }
                 return false;
            }
        });

        // Set isConnected Button !!
        binding.navView.getMenu().getItem(2).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                if (Utils.isInternetConnectionAvailable(MainActivity.this)) {
                    Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Disconnected", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        binding.navView.getMenu().getItem(3).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                Intent intent = new Intent(MainActivity.this, SimulatorActivity.class);
                startActivity(intent);
                return false;
            }
        });
    }

    private void checkIfLandscape() {
        mainViewModel.setIsTwoPaneMode(binding.appBarMain.contentMain.mainActivityLandscapeDetailedItemView != null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        searchMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                showSearchDialog();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void showSearchDialog() {
        final AlertDialog dialog = getSearchDialog();
        dialog.show();
        dialogSearchEditTitle = dialog.findViewById(R.id.dialog_search_title_edit);
        dialogSearchEditAddress = dialog.findViewById(R.id.dialog_search_address_edit);
        dialogSearchEditSurfaceMin = dialog.findViewById(R.id.dialog_search_surface_min_edit);
        dialogSearchEditSurfaceMax = dialog.findViewById(R.id.dialog_search_surface_max_edit);
        dialogSearchEditRoom = dialog.findViewById(R.id.dialog_search_room_edit);
        dialogSearchEditBathroom = dialog.findViewById(R.id.dialog_search_bathroom_edit);
        dialogSearchEditBedroom = dialog.findViewById(R.id.dialog_search_bedroom_edit);
        dialogSearchEditPrice = dialog.findViewById(R.id.dialog_search_price_edit);
        dialogSearchSpinnerType = dialog.findViewById(R.id.dialog_search_type_spinner);
        configurePropertyTypeSpinner();
        dialogSearchSpinnerAgent = dialog.findViewById(R.id.dialog_search_real_estate_agent_spinner);
        configureRealEstateAgentSpinner();
    }

    private void configurePropertyTypeSpinner() {
        mainViewModel.getPropertyTypeList().observe(this, propertyTypes -> {
            propertyTypeList = propertyTypes;

            PropertyTypeAdapter propertyTypeAdapter = new PropertyTypeAdapter(this, propertyTypeList);
            if (dialogSearchSpinnerType != null) {
                dialogSearchSpinnerType.setAdapter(propertyTypeAdapter);
            }

            propertyType = null;

            int idNoChoiceType = propertyTypeList.size() + 1;
            noChoiceType = new PropertyType(idNoChoiceType, "NO TYPE SELECTED");
            propertyTypeList.add(noChoiceType);
            int selection = propertyTypeList.size() - 1;
            dialogSearchSpinnerType.setSelection(selection);

            dialogSearchSpinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        mainViewModel.getRealEstateAgentList().observe(this, realEstateAgents -> {
            realEstateAgentList = realEstateAgents;

            RealEstateAgentAdapter realEstateAgentAdapter = new RealEstateAgentAdapter(this, realEstateAgentList);
            if (dialogSearchSpinnerAgent != null) {
                dialogSearchSpinnerAgent.setAdapter(realEstateAgentAdapter);
            }

            realEstateAgent = null;

            int idNoChoiceAgent = realEstateAgentList.size() + 1;
            noChoiceAgent = new RealEstateAgent(idNoChoiceAgent, "NO AGENT SELECTED", "", "", "");
            realEstateAgentList.add(noChoiceAgent);
            int selection = realEstateAgentList.size() - 1;
            dialogSearchSpinnerAgent.setSelection(selection);

            dialogSearchSpinnerAgent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    realEstateAgent = (RealEstateAgent) adapterView.getItemAtPosition(i);
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {}
            });
        });
    }

    private AlertDialog getSearchDialog() {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, R.style.Dialog);

        alertBuilder.setView(R.layout.dialog_search_properties);
        alertBuilder.setPositiveButton(getString(R.string.dialog_search_button), null);
        alertBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialogSearchEditTitle = null;
                dialogSearchEditAddress = null;
                dialogSearchEditSurfaceMin = null;
                dialogSearchEditSurfaceMax = null;
                dialogSearchEditRoom = null;
                dialogSearchEditBathroom = null;
                dialogSearchEditBedroom = null;
                dialogSearchEditPrice = null;
                dialogSearchSpinnerType = null;
                dialogSearchSpinnerAgent = null;
                searchDialog = null;
                propertyTypeList.remove(noChoiceType);
                realEstateAgentList.remove(noChoiceAgent);
            }
        });

        searchDialog = alertBuilder.create();

        searchDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = searchDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onSearchPositiveButtonClick(searchDialog);
                    }
                });
            }
        });

        return searchDialog;
    }

    private void onSearchPositiveButtonClick(DialogInterface dialogInterface) {
        if (dialogSearchEditTitle != null && dialogSearchEditAddress != null && dialogSearchEditSurfaceMin != null &&
                dialogSearchEditSurfaceMax != null && dialogSearchEditRoom != null && dialogSearchEditBathroom != null &&
                dialogSearchEditBedroom != null && dialogSearchEditPrice != null && dialogSearchSpinnerType != null &&
                dialogSearchSpinnerAgent != null) {
            String mySearchRequestTitle = dialogSearchEditTitle.getText().toString();
            if (mySearchRequestTitle.trim().isEmpty()) {
                mySearchRequestTitle = null;
            }
            String mySearchRequestAddress = dialogSearchEditAddress.getText().toString();
            if (mySearchRequestAddress.trim().isEmpty()) {
                mySearchRequestAddress = null;
            }
            // OK
            String mySearchRequestSurfaceMin = dialogSearchEditSurfaceMin.getText().toString();
            if (mySearchRequestSurfaceMin.trim().isEmpty()) {
                mySearchRequestSurfaceMin = null;
            }
            // OK
            String mySearchRequestSurfaceMax = dialogSearchEditSurfaceMax.getText().toString();
            if (mySearchRequestSurfaceMax.trim().isEmpty()) {
                mySearchRequestSurfaceMax = null;
            }
            // OK
            String mySearchRequestRoom = dialogSearchEditRoom.getText().toString();
            if (mySearchRequestRoom.trim().isEmpty()) {
                mySearchRequestRoom = null;
            }
            // OK
            String mySearchRequestBathroom = dialogSearchEditBathroom.getText().toString();
            if (mySearchRequestBathroom.trim().isEmpty()) {
                mySearchRequestBathroom = null;
            }
            // OK
            String mySearchRequestBedroom = dialogSearchEditBedroom.getText().toString();
            if (mySearchRequestBedroom.trim().isEmpty()) {
                mySearchRequestBedroom = null;
            }
            // OK
            String mySearchRequestPrice = dialogSearchEditPrice.getText().toString();
            if (mySearchRequestPrice.trim().isEmpty()) {
                mySearchRequestPrice = null;
            }
            // OK
            String mySearchRequestTypeId = null;
            if (!propertyType.getName().equals("NO TYPE SELECTED")) {
                mySearchRequestTypeId = String.valueOf(propertyType.getId());
            }
            // OK
            String mySearchRequestAgentId = null;
            if (!realEstateAgent.getName().equals("NO AGENT SELECTED")) {
                mySearchRequestAgentId = String.valueOf(realEstateAgent.getId());
            }
            mainViewModel.getFilteredPropertyList(mySearchRequestTitle, mySearchRequestAddress, mySearchRequestSurfaceMin, mySearchRequestSurfaceMax, mySearchRequestRoom, mySearchRequestBathroom, mySearchRequestBedroom, mySearchRequestPrice, mySearchRequestTypeId, mySearchRequestAgentId);
            dialogInterface.dismiss();
        } else {
            dialogInterface.dismiss();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (noChoiceType != null) {
            propertyTypeList.remove(noChoiceType);
        }
        if (noChoiceAgent != null) {
            realEstateAgentList.remove(noChoiceAgent);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}