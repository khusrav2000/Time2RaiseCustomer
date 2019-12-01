package com.time2raise.customer.data.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "order_on_cart")
public class OrderToCart {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "auto_order_id")
    int autoOrderId;

    @ColumnInfo(name = "event_id")
    int eventId;

    @ColumnInfo(name = "amount")
    int amount;

    @ColumnInfo(name = "food_name")
    String foodName;

    @ColumnInfo(name = "food_size_name")
    String foodSizeName;

    @ColumnInfo(name = "price")
    double price;

    @ColumnInfo(name = "food_category_id")
    int foodCategoryId;

    @ColumnInfo(name = "order_number")
    int orderNumber;

    @ColumnInfo(name = "food_size_id")
    int foodSizeId;

    public OrderToCart(int eventId, int amount, String foodName, String foodSizeName, double price, int foodCategoryId, int orderNumber, int foodSizeId) {
        this.eventId = eventId;
        this.amount = amount;
        this.foodName = foodName;
        this.foodSizeName = foodSizeName;
        this.price = price;
        this.foodCategoryId = foodCategoryId;
        this.orderNumber = orderNumber;
        this.foodSizeId = foodSizeId;
    }

    public int getAutoOrderId() {
        return autoOrderId;
    }

    public void setAutoOrderId(int autoOrderId) {
        this.autoOrderId = autoOrderId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodSizeName() {
        return foodSizeName;
    }

    public void setFoodSizeName(String foodSizeName) {
        this.foodSizeName = foodSizeName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getFoodCategoryId() {
        return foodCategoryId;
    }

    public void setFoodCategoryId(int foodCategoryId) {
        this.foodCategoryId = foodCategoryId;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public int getFoodSizeId() {
        return foodSizeId;
    }

    public void setFoodSizeId(int foodSizeId) {
        this.foodSizeId = foodSizeId;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public String toString() {
        return "OrderToCart{" +
                "autoOrderId=" + autoOrderId +
                ", eventId=" + eventId +
                ", amount=" + amount +
                ", foodName='" + foodName + '\'' +
                ", foodSizeName='" + foodSizeName + '\'' +
                ", price=" + price +
                ", foodCategoryId=" + foodCategoryId +
                ", orderNumber=" + orderNumber +
                ", foodSizeId=" + foodSizeId +
                '}';
    }
}
