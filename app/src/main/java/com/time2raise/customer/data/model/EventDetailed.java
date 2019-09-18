package com.time2raise.customer.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventDetailed {
    @SerializedName("EventId")
    @Expose
    private int eventId;

    @SerializedName("OrgId")
    @Expose
    private int resId;

    @SerializedName("RequestId")
    @Expose
    private int requestId;

    @SerializedName("Name")
    @Expose
    private String name;

    @SerializedName("Date")
    @Expose
    private String date;

    @SerializedName("Start")
    @Expose
    private String start;

    @SerializedName("End")
    @Expose
    private String end;

    @SerializedName("About")
    @Expose
    private String about;

    @SerializedName("Photos")
    @Expose
    private List<Photo> photo;

    @SerializedName("InitProfilIcon")
    @Expose
    private String initProfileIconUrl;

    @SerializedName("OrgProfilIcon")
    @Expose
    private String resProfileIconUrl;

    @SerializedName("InitName")
    @Expose
    private String initName;

    @SerializedName("OrgName")
    @Expose
    private String resName;

    @SerializedName("InitId")
    @Expose
    private int initId;

    public EventDetailed(int eventId, int resId, int requestId, String name, String date, String start, String end, String about, List<Photo> photo, String initProfileIconUrl, String resProfileIconUrl, String initName, String resName, int initId) {
        this.eventId = eventId;
        this.resId = resId;
        this.requestId = requestId;
        this.name = name;
        this.date = date;
        this.start = start;
        this.end = end;
        this.about = about;
        this.photo = photo;
        this.initProfileIconUrl = initProfileIconUrl;
        this.resProfileIconUrl = resProfileIconUrl;
        this.initName = initName;
        this.resName = resName;
        this.initId = initId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public List<Photo> getPhoto() {
        return photo;
    }

    public void setPhoto(List<Photo> photo) {
        this.photo = photo;
    }

    public String getInitProfileIconUrl() {
        return initProfileIconUrl;
    }

    public void setInitProfileIconUrl(String initProfileIconUrl) {
        this.initProfileIconUrl = initProfileIconUrl;
    }

    public String getResProfileIconUrl() {
        return resProfileIconUrl;
    }

    public void setResProfileIconUrl(String resProfileIconUrl) {
        this.resProfileIconUrl = resProfileIconUrl;
    }

    public String getInitName() {
        return initName;
    }

    public void setInitName(String initName) {
        this.initName = initName;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public int getInitId() {
        return initId;
    }

    public void setInitId(int initId) {
        this.initId = initId;
    }

    @Override
    public String toString() {
        return "EventDetailed{" +
                "eventId=" + eventId +
                ", resId=" + resId +
                ", requestId=" + requestId +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", about='" + about + '\'' +
                ", photo=" + photo +
                ", initProfileIconUrl='" + initProfileIconUrl + '\'' +
                ", resProfileIconUrl='" + resProfileIconUrl + '\'' +
                ", initName='" + initName + '\'' +
                ", resName='" + resName + '\'' +
                ", initId=" + initId +
                '}';
    }
}
