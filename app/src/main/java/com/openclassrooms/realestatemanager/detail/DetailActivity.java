package com.openclassrooms.realestatemanager.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.openclassrooms.realestatemanager.ViewModelFactory;
import com.openclassrooms.realestatemanager.databinding.ActivityDetailBinding;
import com.openclassrooms.realestatemanager.model.Property;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;

    private DetailViewModel detailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        detailViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(this)).get(DetailViewModel.class);

        getInfoFromIntent();
        setPreviousPageButton();

    }

    void getInfoFromIntent() {
        Intent intent = getIntent();
        Bundle myBundle = intent.getBundleExtra("BUNDLE_ITEM_TO_DETAIL");
        Property property = (Property) myBundle.get("PROPERTY_ITEM");
        detailViewModel.setMyProperty(property);
    }

    void setPreviousPageButton() {
        binding.detailActivityPreviousPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}