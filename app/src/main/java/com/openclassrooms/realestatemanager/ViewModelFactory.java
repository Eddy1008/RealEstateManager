package com.openclassrooms.realestatemanager;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.detail.DetailViewModel;
import com.openclassrooms.realestatemanager.main.MainViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory factory;

    public static  ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory();
                }
            }
        }
        return factory;
    }

    // Repositories
    // ...


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DetailViewModel.class)) {
            return (T) new DetailViewModel();
        }

        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel();
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
