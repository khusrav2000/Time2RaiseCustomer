package com.time2raise.customer;

import com.time2raise.customer.data.model.OneFoodInfo;
import com.time2raise.customer.data.model.Order;
import com.time2raise.customer.data.model.ResFood;
import com.time2raise.customer.data.room.OrderToCart;

import java.util.ArrayList;
import java.util.List;

public class CalculateCount {

    static List<OneFoodInfo> foods = new ArrayList<>();

    public static void clearClass(){
        foods.clear();
    }

    public static void addFood(OneFoodInfo oneFoodInfo){
        foods.add(oneFoodInfo);
    }

    public static OneFoodInfo getFoodsByNumber(int i){
        return foods.get(i);
    }
}
