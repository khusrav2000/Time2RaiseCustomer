package com.time2raise.customer.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventDetailed {
    @SerializedName("EventId")
    @Expose
    private int eventId;

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

    @SerializedName("InitiatorInfo")
    @Expose
    private InitiatorDetailed initiatorInfo;

    @SerializedName("RestouranInfo")
    @Expose
    private RestaurantDetailed restaurantInfo;

    public EventDetailed(int eventId, int requestId, String name, String date, String start, String end, String about, List<Photo> photo, InitiatorDetailed initiatorInfo, RestaurantDetailed restaurantInfo) {
        this.eventId = eventId;
        this.requestId = requestId;
        this.name = name;
        this.date = date;
        this.start = start;
        this.end = end;
        this.about = about;
        this.photo = photo;
        this.initiatorInfo = initiatorInfo;
        this.restaurantInfo = restaurantInfo;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
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

    public InitiatorDetailed getInitiatorInfo() {
        return initiatorInfo;
    }

    public void setInitiatorInfo(InitiatorDetailed initiatorInfo) {
        this.initiatorInfo = initiatorInfo;
    }

    public RestaurantDetailed getRestaurantInfo() {
        return restaurantInfo;
    }

    public void setRestaurantInfo(RestaurantDetailed restaurantInfo) {
        this.restaurantInfo = restaurantInfo;
    }

    @Override
    public String toString() {
        return "EventDetailed{" +
                "eventId=" + eventId +
                ", requestId=" + requestId +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", about='" + about + '\'' +
                ", photo=" + photo +
                ", initiatorInfo=" + initiatorInfo +
                ", restaurantInfo=" + restaurantInfo +
                '}';
    }
}
