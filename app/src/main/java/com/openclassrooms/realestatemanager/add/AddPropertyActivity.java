package com.openclassrooms.realestatemanager.add;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ViewModelFactory;
import com.openclassrooms.realestatemanager.databinding.ActivityAddPropertyBinding;

public class AddPropertyActivity extends AppCompatActivity {

    private ActivityAddPropertyBinding binding;

    private AddPropertyViewModel addPropertyViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddPropertyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addPropertyViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(this)).get(AddPropertyViewModel.class);

        setPreviousPageButton();
    }

    void setPreviousPageButton() {
        binding.activityAddPropertyPreviousPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}