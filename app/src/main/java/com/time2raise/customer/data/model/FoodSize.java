package com.time2raise.customer.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class FoodSize {
    @SerializedName("FoodSizeId")
    @Expose
    private int foodSizeId;

    @SerializedName("Name")
    @Expose
    private String sizeName;

    public FoodSize(int foodSizeId, String sizeName) {
        this.foodSizeId = foodSizeId;
        this.sizeName = sizeName;
    }

    public int getFoodSizeId() {
        return foodSizeId;
    }

    public void setFoodSizeId(int foodSizeId) {
        this.foodSizeId = foodSizeId;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    @Override
    public String toString() {
        return "FoodSize{" +
                "foodSizeId=" + foodSizeId +
                ", sizeName='" + sizeName + '\'' +
                '}';
    }
}
