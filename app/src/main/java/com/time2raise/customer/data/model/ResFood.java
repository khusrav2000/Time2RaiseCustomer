package com.time2raise.customer.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResFood {
    @SerializedName("OrgFood_ID")
    @Expose
    private int resFoodId;

    @SerializedName("FoodName")
    @Expose
    private String foodName;

    @SerializedName("FoodCategory")
    @Expose
    private int foodCategory;

    @SerializedName("FoodsInfo")
    @Expose
    private List<FoodSizesInformation> foodSizesInformation;

    @SerializedName("OrgID")
    @Expose
    private int orgId; // Скорее всего это id ресторана, которая готовить эту еду.

    @SerializedName("FoodDescription")
    @Expose
    private String foodDescription;

    @SerializedName("ImageUrl")
    @Expose
    private List<Photo> foodPhotos;

    public ResFood(int resFoodId, String foodName, int foodCategory, List<FoodSizesInformation> foodSizesInformation, int orgId, String foodDescription, List<Photo> foodPhotos) {
        this.resFoodId = resFoodId;
        this.foodName = foodName;
        this.foodCategory = foodCategory;
        this.foodSizesInformation = foodSizesInformation;
        this.orgId = orgId;
        this.foodDescription = foodDescription;
        this.foodPhotos = foodPhotos;
    }

    public int getResFoodId() {
        return resFoodId;
    }

    public void setResFoodId(int resFoodId) {
        this.resFoodId = resFoodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(int foodCategory) {
        this.foodCategory = foodCategory;
    }

    public List<FoodSizesInformation> getFoodSizesInformation() {
        return foodSizesInformation;
    }

    public void setFoodSizesInformation(List<FoodSizesInformation> foodSizesInformation) {
        this.foodSizesInformation = foodSizesInformation;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public List<Photo> getFoodPhotos() {
        return foodPhotos;
    }

    public void setFoodPhotos(List<Photo> foodPhotos) {
        this.foodPhotos = foodPhotos;
    }

    @Override
    public String toString() {
        return "ResFood{" +
                "resFoodId=" + resFoodId +
                ", foodName='" + foodName + '\'' +
                ", foodCategory=" + foodCategory +
                ", foodSizesInformation=" + foodSizesInformation +
                ", orgId=" + orgId +
                ", foodDescription='" + foodDescription + '\'' +
                ", foodPhotos=" + foodPhotos +
                '}';
    }
}
