package com.time2raise.customer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Profile extends AppCompatActivity {


    Button saveProfileInformation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        saveProfileInformation = findViewById(R.id.save_profile_information);

        saveProfileInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainPageActivity();
            }
        });
    }

    private void startMainPageActivity() {
        Intent intent = new Intent(this, MainPageActivity.class);
        startActivity(intent);
    }
}
