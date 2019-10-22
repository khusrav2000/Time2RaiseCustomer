package com.time2raise.customer.data.model;

public class OneFoodSubFoodSizes {
    private int amount;
    private String foodSizeName;
    private double price;

    public OneFoodSubFoodSizes(int amount, String foodSizeName, double price) {
        this.amount = amount;
        this.foodSizeName = foodSizeName;
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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

    @Override
    public String toString() {
        return "OneFoodSubFoodSizes{" +
                "amount=" + amount +
                ", foodSizeName='" + foodSizeName + '\'' +
                ", price=" + price +
                '}';
    }
}
