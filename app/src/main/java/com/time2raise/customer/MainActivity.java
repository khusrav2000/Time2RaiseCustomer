package com.time2raise.customer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        if (true){
            Intent intent = new Intent(this, MainPageActivity.class);
            startActivity(intent);
        } else {

        }
    }
}
