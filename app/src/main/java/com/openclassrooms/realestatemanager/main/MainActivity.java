package com.openclassrooms.realestatemanager.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ViewModelFactory;
import com.openclassrooms.realestatemanager.databinding.ActivityMainBinding;
import com.openclassrooms.realestatemanager.detail.DetailActivity;
import com.openclassrooms.realestatemanager.detail.ui.itemtodetail.ItemFragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AppBarConfiguration mAppBarConfiguration;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mainViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(this)).get(MainViewModel.class);

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

        checkIfLandscape(savedInstanceState);

        // Set Localise Me Button !!
        binding.navView.getMenu().getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                // TODO localise me button
                Toast.makeText(MainActivity.this, "Will redirect to allow geo localisation activity !", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Set Hello You Button !!
        binding.navView.getMenu().getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                // TODO add a property
                Toast.makeText(MainActivity.this, "Hello you !", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void checkIfLandscape(Bundle savedInstanceState) {
        if (binding.appBarMain.contentMain.mainActivityLandscapeDetailedItemView != null) {
            mainViewModel.setIsTwoPaneMode(true);
        } else {
            mainViewModel.setIsTwoPaneMode(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchViewBox = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchViewBox.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                // TODO research submit
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                // TODO reseach text change
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}