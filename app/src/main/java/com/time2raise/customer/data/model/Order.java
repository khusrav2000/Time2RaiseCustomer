package com.time2raise.customer.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order {
    @SerializedName("OrderID")
    @Expose
    private int orderId;

    @SerializedName("EventID")
    @Expose
    private int eventId;

    @SerializedName("CustomertID")
    @Expose
    private int customerId;

    @SerializedName("TotalPrice")
    @Expose
    private double totalPrice;

    @SerializedName("NumberOrder")
    @Expose
    private int numberOrder;

    @SerializedName("Created")
    @Expose
    private String createDate;

    @SerializedName("OrderInfo")
    @Expose
    private List<OrderInformation> orderInformationList;

    public Order(int orderId, int eventId, int customerId, double totalPrice, int numberOrder, String createDate, List<OrderInformation> orderInformationList) {
        this.orderId = orderId;
        this.eventId = eventId;
        this.customerId = customerId;
        this.totalPrice = totalPrice;
        this.numberOrder = numberOrder;
        this.createDate = createDate;
        this.orderInformationList = orderInformationList;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getNumberOrder() {
        return numberOrder;
    }

    public void setNumberOrder(int numberOrder) {
        this.numberOrder = numberOrder;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public List<OrderInformation> getOrderInformationList() {
        return orderInformationList;
    }

    public void setOrderInformationList(List<OrderInformation> orderInformationList) {
        this.orderInformationList = orderInformationList;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", eventId=" + eventId +
                ", customerId=" + customerId +
                ", totalPrice=" + totalPrice +
                ", numberOrder=" + numberOrder +
                ", createDate='" + createDate + '\'' +
                ", orderInformationList=" + orderInformationList +
                '}';
    }
}
