package com.openclassrooms.realestatemanager.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DetailViewModel extends ViewModel {

    // Repository

    // Data
    private final MutableLiveData<String> myUserName = new MutableLiveData<>();
    public void setMyUserName(String userName) {
        myUserName.setValue(userName);
    }
    public LiveData<String> getMyUserNameLiveData() {
        return myUserName;
    }


}
