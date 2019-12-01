package com.time2raise.customer.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderToSend {
    @SerializedName("EventID")
    @Expose
    private int eventId;

    @SerializedName("OrdItem")
    @Expose
    private List<OrderToSendItem> orderItem;

    public OrderToSend(int eventId, List<OrderToSendItem> orderItem) {
        this.eventId = eventId;
        this.orderItem = orderItem;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public List<OrderToSendItem> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(List<OrderToSendItem> orderItem) {
        this.orderItem = orderItem;
    }

    @Override
    public String toString() {
        return "OrderToSend{" +
                "eventId=" + eventId +
                ", orderItem=" + orderItem +
                '}';
    }
}
