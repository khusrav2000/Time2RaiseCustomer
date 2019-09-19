package com.time2raise.customer.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InitiatorDetailed {
    @SerializedName("InitId")
    @Expose
    private int initId;

    @SerializedName("Name")
    @Expose
    private String name;

    @SerializedName("Icon")
    @Expose
    private String icon;

    @SerializedName("Address")
    @Expose
    private String address;

    public InitiatorDetailed(int initId, String name, String icon, String address) {
        this.initId = initId;
        this.name = name;
        this.icon = icon;
        this.address = address;
    }

    public int getInitId() {
        return initId;
    }

    public void setInitId(int initId) {
        this.initId = initId;
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
        return "InitiatorDetailed{" +
                "initId=" + initId +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
