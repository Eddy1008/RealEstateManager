package com.openclassrooms.realestatemanager.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class RealEstateAgent {

    @PrimaryKey
    private long id;
    private String name;
    private String mail;
    private String avatarUrl;
    private String phoneNumber;

    public RealEstateAgent(long id, String name, String mail, String avatarUrl, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.avatarUrl = avatarUrl;
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
