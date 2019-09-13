package com.time2raise.customer.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderInformation {
    @SerializedName("OrderInfo_id")
    @Expose
    private int orderInfoId;

    @SerializedName("OrderId")
    @Expose
    private int orderId;

    @SerializedName("FoodsCategoryID")
    @Expose
    private int foodsCategoryId;

    @SerializedName("FoodSizeName")
    @Expose
    private String foodSizeName;

    @SerializedName("FoodName")
    @Expose
    private String foodName;

    @SerializedName("Price")
    @Expose
    private double price;

    @SerializedName("Amount")
    @Expose
    private int amount;

    public OrderInformation(int orderInfoId, int orderId, int foodsCategoryId, String foodSizeName, String foodName, double price, int amount) {
        this.orderInfoId = orderInfoId;
        this.orderId = orderId;
        this.foodsCategoryId = foodsCategoryId;
        this.foodSizeName = foodSizeName;
        this.foodName = foodName;
        this.price = price;
        this.amount = amount;
    }

    public int getOrderInfoId() {
        return orderInfoId;
    }

    public void setOrderInfoId(int orderInfoId) {
        this.orderInfoId = orderInfoId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getFoodsCategoryId() {
        return foodsCategoryId;
    }

    public void setFoodsCategoryId(int foodsCategoryId) {
        this.foodsCategoryId = foodsCategoryId;
    }

    public String getFoodSizeName() {
        return foodSizeName;
    }

    public void setFoodSizeName(String foodSizeName) {
        this.foodSizeName = foodSizeName;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "OrderInformation{" +
                "orderInfoId=" + orderInfoId +
                ", orderId=" + orderId +
                ", foodsCategoryId=" + foodsCategoryId +
                ", foodSizeName='" + foodSizeName + '\'' +
                ", foodName='" + foodName + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }
}
