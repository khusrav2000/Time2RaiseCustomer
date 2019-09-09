package com.time2raise.customer.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Photo {
    @SerializedName("Url")
    @Expose
    private String url;

    @SerializedName("Ismain")
    @Expose
    private boolean isMain;

    @SerializedName("Type")
    @Expose
    private String type;

    public Photo(String url, boolean isMain, String type) {
        this.url = url;
        this.isMain = isMain;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "url='" + url + '\'' +
                ", isMain=" + isMain +
                ", type='" + type + '\'' +
                '}';
    }
}
