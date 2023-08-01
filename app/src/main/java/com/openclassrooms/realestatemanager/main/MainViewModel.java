package com.openclassrooms.realestatemanager.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    // Repository

    // Data
    private final MutableLiveData<Boolean> isTwoPaneMode = new MutableLiveData<>();
    public void setIsTwoPaneMode(boolean isTwoPane) {
        isTwoPaneMode.setValue(isTwoPane);
    }
    public LiveData<Boolean> getIsTwoPaneMode() {
        return isTwoPaneMode;
    }
}
