package com.time2raise.customer.data.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.time2raise.customer.data.model.Order;
import com.time2raise.customer.data.room.OrderToCart;

import java.util.List;

@Dao
public interface OrderToCartDao {
    @Insert
    long insertOrder(OrderToCart orderToCart);

    @Query("SELECT * FROM order_on_cart WHERE event_id = :eventId")
    List<OrderToCart> getOrdersByEventId(int eventId);

    @Query("DELETE FROM order_on_cart WHERE auto_order_id = :autoOrderId")
    void deleteFood(int autoOrderId);

}
