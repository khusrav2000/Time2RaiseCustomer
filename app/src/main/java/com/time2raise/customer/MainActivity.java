package com.time2raise.customer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public static final String APP_PREFERENCES = "SkipLoginPhone";

    public static final String APP_PREFERENCES_PHONE= "getPhone";

    SharedPreferences skipLoginPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        skipLoginPhone = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        startNextActivity();
                    }
                },
                3000
        );
    }

    public void startNextActivity(){
        String phone = skipLoginPhone.getString(APP_PREFERENCES_PHONE, "");
        if (!phone.equals("")){
            Intent intent = new Intent(this, MainPageActivity.class);
            intent.putExtra("phone", phone);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, VerifyPhoneNumber.class);
            startActivity(intent);
        }
    }
}
