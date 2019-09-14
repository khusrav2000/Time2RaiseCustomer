package com.time2raise.customer.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrganizerInformation {
    @SerializedName("InitId")
    @Expose
    private int initId;

    @SerializedName("Email")
    @Expose
    private String email;

    @SerializedName("Address")
    @Expose
    private String address;

    @SerializedName("Phone")
    @Expose
    private String phone;

    @SerializedName("Name")
    @Expose
    private String name;

    @SerializedName("ZipCode")
    @Expose
    private int zipCode;

    @SerializedName("About")
    @Expose
    private String about;

    @SerializedName("GaleryUrl")
    @Expose
    private String galleryUrl;

    @SerializedName("IconUrl")
    @Expose
    private String iconUrl;

    @SerializedName("BackgrounImageUrl")
    @Expose
    private String backgroundImageUrl;

    @SerializedName("DeviceId")
    @Expose
    private String deviceId;

    public OrganizerInformation(int initId, String email, String address, String phone, String name, int zipCode, String about, String galleryUrl, String iconUrl, String backgroundImageUrl, String deviceId) {
        this.initId = initId;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.name = name;
        this.zipCode = zipCode;
        this.about = about;
        this.galleryUrl = galleryUrl;
        this.iconUrl = iconUrl;
        this.backgroundImageUrl = backgroundImageUrl;
        this.deviceId = deviceId;
    }

    public int getInitId() {
        return initId;
    }

    public void setInitId(int initId) {
        this.initId = initId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getGalleryUrl() {
        return galleryUrl;
    }

    public void setGalleryUrl(String galleryUrl) {
        this.galleryUrl = galleryUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getBackgroundImageUrl() {
        return backgroundImageUrl;
    }

    public void setBackgroundImageUrl(String backgroundImageUrl) {
        this.backgroundImageUrl = backgroundImageUrl;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "OrganizerInformation{" +
                "initId=" + initId +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", zipCode=" + zipCode +
                ", about='" + about + '\'' +
                ", galleryUrl='" + galleryUrl + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", backgroundImageUrl='" + backgroundImageUrl + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}
