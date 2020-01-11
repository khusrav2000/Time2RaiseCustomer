package com.time2raise.customer.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.time2raise.customer.events.ListEventsFragment;

import java.util.List;

public class EventInf {
    @SerializedName("EventId")
    @Expose
    private int eventId;

    @SerializedName("OrgId")
    @Expose
    private int orgId;

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

    @SerializedName("ZipCode")
    @Expose
    private int zipCode;

    public EventInf(int eventId, int orgId, int requestId, String name, String date, String start, String end, String about, List<Photo> photo, int zipCode) {
        this.eventId = eventId;
        this.orgId = orgId;
        this.requestId = requestId;
        this.name = name;
        this.date = date;
        this.start = start;
        this.end = end;
        this.about = about;
        this.photo = photo;
        this.zipCode = zipCode;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
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

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "EventInf{" +
                "eventId=" + eventId +
                ", orgId=" + orgId +
                ", requestId=" + requestId +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", about='" + about + '\'' +
                ", photo=" + photo +
                ", zipCode=" + zipCode +
                '}';
    }
}
