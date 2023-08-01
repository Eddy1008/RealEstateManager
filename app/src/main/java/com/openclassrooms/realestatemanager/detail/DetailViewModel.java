package com.openclassrooms.realestatemanager.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DetailViewModel extends ViewModel {

    // Repository

    // Data
    private final MutableLiveData<String> myItemInfo = new MutableLiveData<>();
    public void setMyItemInfo(String itemInfo) {
        myItemInfo.setValue(itemInfo);
    }
    public LiveData<String> getMyItemInfoLiveData() {
        return myItemInfo;
    }


    private final MutableLiveData<String> myItemPhoto = new MutableLiveData<>();
    public void setMyItemPhoto(String itemPhoto) {
        myItemPhoto.setValue(itemPhoto);
    }
    public LiveData<String> getMyItemPhotoLiveData() {
        return myItemPhoto;
    }


}
