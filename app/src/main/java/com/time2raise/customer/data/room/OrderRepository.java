package com.time2raise.customer.data.room;

import android.app.Application;
import android.os.AsyncTask;

import com.time2raise.customer.data.room.dao.OrderToCartDao;

import java.util.List;

public class OrderRepository {

    private OrderToCartDao orderToCartDao;
    public OrderRepository(Application application){

        OrderDatabase db = OrderDatabase.getDatabase(application);
        orderToCartDao = db.OrderToCartDao();

    }

    public void insert (OrderToCart orderToCart) {
        new insertOrderToCart(orderToCartDao).execute(orderToCart);
    }

    private static class insertOrderToCart extends AsyncTask<OrderToCart, Void, Void> {

        private OrderToCartDao mAsyncTaskDao;

        insertOrderToCart(OrderToCartDao dao) {
            mAsyncTaskDao = dao;
        }



        @Override
        protected Void doInBackground(OrderToCart... orderToCarts) {
            mAsyncTaskDao.insertOrder(orderToCarts[0]);

            return null;
        }
    }

    public void delete(Integer autoOrderId) {
        new deleteFood(orderToCartDao).execute(autoOrderId);
    }

    private static class deleteFood extends AsyncTask<Integer, Void, Void> {

        private OrderToCartDao mAsyncTaskDao;

        deleteFood(OrderToCartDao dao) {
            mAsyncTaskDao = dao;
        }



        @Override
        protected Void doInBackground(Integer... autoOrderId) {
            mAsyncTaskDao.deleteFood(autoOrderId[0]);
            return null;
        }
    }

    public List<OrderToCart> getOrdersByEventId(int eventId){
        return this.orderToCartDao.getOrdersByEventId(eventId);
    }

}
