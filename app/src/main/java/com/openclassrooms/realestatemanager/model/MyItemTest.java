package com.openclassrooms.realestatemanager.model;

public class MyItemTest {

    private String info;
    private String photo;

    public MyItemTest(String info, String photo) {
        this.info = info;
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
}
