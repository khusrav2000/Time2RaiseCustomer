package com.time2raise.customer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class VerifyPhoneNumber extends AppCompatActivity {

    Spinner phoneCodes;
    EditText selectedPhoneCode;
    EditText phoneNumber;
    Button sendVerificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_number);

        sendVerificationCode = findViewById(R.id.send_verification_code);

        sendVerificationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEnterVerificationCode();
            }
        });

    }

    private void startEnterVerificationCode() {
        Intent intent = new Intent(this, VerificationCode.class);
        startActivity(intent);
    }
}
