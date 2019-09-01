package com.time2raise.customer.data.apis;

import com.time2raise.customer.data.model.Message;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Customer {

    @GET("/api/customer/valid/{phone}")
    Call<Message> validationPhone(@Path("phone") String phone);

    @GET("/api/customer/send/verificationcode/{phone}")
    Call<Message> getVerificationCode(@Path("phone") String phone);

    @GET("/api/customer/valid/{phone}/{code}")
    Call<Message> checkVerificationCode(@Path("phone") String phone, @Path("code") String code);

}
