package com.time2raise.customer.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantDetailed {
    @SerializedName("RestouranID")
    @Expose
    private int resId;

    @SerializedName("Name")
    @Expose
    private String name;

    @SerializedName("Icon")
    @Expose
    private String icon;

    @SerializedName("Address")
    @Expose
    private String address;

    public RestaurantDetailed(int resId, String name, String icon, String address) {
        this.resId = resId;
        this.name = name;
        this.icon = icon;
        this.address = address;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "RestaurantDetailed{" +
                "resId=" + resId +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
