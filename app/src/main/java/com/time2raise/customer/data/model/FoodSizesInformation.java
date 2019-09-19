package com.time2raise.customer.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodSizesInformation {
    @SerializedName("FoodInfoID")
    @Expose
    private int foodId;

    @SerializedName("OrgFoodID")
    @Expose
    private int orgFoodId;

    @SerializedName("Size")
    @Expose
    private FoodSize foodSizes;

    @SerializedName("CookTime")
    @Expose
    private String cookTime;

    @SerializedName("PackingTime")
    @Expose
    private String packingTime;

    @SerializedName("Price")
    @Expose
    private double price;

    public FoodSizesInformation(int foodId, int orgFoodId, FoodSize foodSizes, String cookTime, String packingTime, double price) {
        this.foodId = foodId;
        this.orgFoodId = orgFoodId;
        this.foodSizes = foodSizes;
        this.cookTime = cookTime;
        this.packingTime = packingTime;
        this.price = price;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getOrgFoodId() {
        return orgFoodId;
    }

    public void setOrgFoodId(int orgFoodId) {
        this.orgFoodId = orgFoodId;
    }

    public FoodSize getFoodSizes() {
        return foodSizes;
    }

    public void setFoodSizes(FoodSize foodSizes) {
        this.foodSizes = foodSizes;
    }

    public String getCookTime() {
        return cookTime;
    }

    public void setCookTime(String cookTime) {
        this.cookTime = cookTime;
    }

    public String getPackingTime() {
        return packingTime;
    }

    public void setPackingTime(String packingTime) {
        this.packingTime = packingTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "FoodSizesInformation{" +
                "foodId=" + foodId +
                ", orgFoodId=" + orgFoodId +
                ", foodSizes=" + foodSizes +
                ", cookTime='" + cookTime + '\'' +
                ", packingTime='" + packingTime + '\'' +
                ", price=" + price +
                '}';
    }
}
