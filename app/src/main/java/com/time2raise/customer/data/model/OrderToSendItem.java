package com.time2raise.customer.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderToSendItem {
    @SerializedName("OrgFoodInfoID")
    @Expose
    private int foodSizeId;

    @SerializedName("Amount")
    @Expose
    private int amount;

    public OrderToSendItem(int foodSizeId, int amount) {
        this.foodSizeId = foodSizeId;
        this.amount = amount;
    }

    public int getFoodSizeId() {
        return foodSizeId;
    }

    public void setFoodSizeId(int foodSizeId) {
        this.foodSizeId = foodSizeId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "OrderToSendItem{" +
                "foodSizeId=" + foodSizeId +
                ", amount=" + amount +
                '}';
    }
}
