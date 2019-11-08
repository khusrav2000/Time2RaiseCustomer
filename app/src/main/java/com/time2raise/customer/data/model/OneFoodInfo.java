package com.time2raise.customer.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OneFoodInfo {

    private String foodName;
    private double totalPrice;
    private List<OneFoodSubFoodSizes> foodSizes;
    private int eventId;

    public OneFoodInfo(String foodName, double totalPrice, List<OneFoodSubFoodSizes> foodSizes, int eventId) {
        this.foodName = foodName;
        this.totalPrice = totalPrice;
        this.foodSizes = foodSizes;
        this.eventId = eventId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPtice) {
        this.totalPrice = totalPtice;
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
                ", totalPtice=" + totalPrice +
                ", foodSizes=" + foodSizes +
                ", eventId=" + eventId +
                '}';
    }
}
