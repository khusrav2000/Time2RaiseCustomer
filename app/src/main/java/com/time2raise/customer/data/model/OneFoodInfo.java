package com.time2raise.customer.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OneFoodInfo {

    private String foodName;
    private double totalPtice;
    private List<OneFoodSubFoodSizes> foodSizes;
    private int eventId;

    public OneFoodInfo(String foodName, double totalPtice, List<OneFoodSubFoodSizes> foodSizes, int eventId) {
        this.foodName = foodName;
        this.totalPtice = totalPtice;
        this.foodSizes = foodSizes;
        this.eventId = eventId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getTotalPtice() {
        return totalPtice;
    }

    public void setTotalPtice(double totalPtice) {
        this.totalPtice = totalPtice;
    }

    public List<OneFoodSubFoodSizes> getFoodSizes() {
        return foodSizes;
    }

    public void setFoodSizes(List<OneFoodSubFoodSizes> foodSizes) {
        this.foodSizes = foodSizes;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    @Override
    public String toString() {
        return "OneFoodInfo{" +
                "foodName='" + foodName + '\'' +
                ", totalPtice=" + totalPtice +
                ", foodSizes=" + foodSizes +
                ", eventId=" + eventId +
                '}';
    }
}
