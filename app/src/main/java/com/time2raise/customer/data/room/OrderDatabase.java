package com.time2raise.customer.data.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.time2raise.customer.Constants;
import com.time2raise.customer.data.room.dao.OrderToCartDao;


@Database(entities = {OrderToCart.class}, version = 1,exportSchema = false)

public abstract class OrderDatabase extends RoomDatabase {

    public abstract OrderToCartDao OrderToCartDao();

    private static volatile OrderDatabase INSTANCE;

    static OrderDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (OrderDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            OrderDatabase.class, Constants.DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
