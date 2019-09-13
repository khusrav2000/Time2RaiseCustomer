package com.time2raise.customer.data.apis;

import android.support.v4.content.res.FontResourcesParserCompat;

import com.time2raise.customer.Event;
import com.time2raise.customer.data.model.EventInformation;
import com.time2raise.customer.data.model.Message;
import com.time2raise.customer.data.model.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface Customer {

    // Проверка того, что этот номер не используется в этом приложении.
    @GET("/api/customer/valid/{phone}")
    Call<Message> validationPhone(@Path("phone") String phone);

    // Отправка номера телефона для получения смс кода.
    @GET("/api/customer/send/verificationcode/{phone}")
    Call<Message> getVerificationCode(@Path("phone") String phone);

    // Отправка номера телефона и полученного кода из смс, для проверки правильности.
    // Если пользователь ввел правильный код из смс, то в ответ приходить токен.
    @GET("/api/customer/valid/{phone}/{code}")
    Call<Message> checkVerificationCode(@Path("phone") String phone, @Path("code") String code);

    // Загрузка event-ов в количестве по заданному лимиту
    @GET("api/customer/events/{limit}")
    Call< List<EventInformation> > getEvents(@Header ("token") String token, @Path("limit") int limit);

    // Получения данные о "событии" по id
    @GET("api/customer/event/{id}")
    Call< EventInformation > getEventById(@Path("id") int id);

    // Загрузка ongoingOrders в количестве limit штук.
    @GET("api/customer/order/ongoing/{limit}")
    Call< List<Order> > getOngoingOrders(@Header("token") String token, @Path("limit") int limit);

    //TODO: Загрузка pastOrders в количестве limit штук.
    @GET("api/customer/order/{limit}")
    Call< List<Order> > getPastOrders(@Header("token") String token, @Path("limit") int limit);

}
