package com.time2raise.customer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.time2raise.customer.data.NetworkClient;
import com.time2raise.customer.data.apis.Customer;
import com.time2raise.customer.data.model.CustomerInformation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FillProfileInformation extends AppCompatActivity {

    public static final String APP_PREFERENCES = "SkipLoginPhone";
    public static final String APP_PREFERENCES_PHONE= "getPhone";
    public static final String APP_TOKEN = "getToken";
    public static final String APP_CUSTOMER_ID = "id";
    SharedPreferences skipLoginPhone;

    Button saveProfileInformation;
    EditText clientPhone;
    EditText clientFirstName;
    EditText clientLastName;
    EditText clientEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_profile_information);

        saveProfileInformation      = findViewById(R.id.save_profile_information);
        clientPhone                 = findViewById(R.id.edit_client_phone);
        clientFirstName             = findViewById(R.id.edit_client_first_name);
        clientLastName              = findViewById(R.id.edit_client_last_name);
        clientEmail                 = findViewById(R.id.edit_client_email);


        skipLoginPhone = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String phone = skipLoginPhone.getString(APP_PREFERENCES_PHONE, "");
        clientPhone.setText(phone);


        saveProfileInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainPageActivity();
            }
        });
    }

    private void startMainPageActivity() {
        if (validInformation()){
            CustomerInformation customerInformation = new CustomerInformation(
                    0, clientFirstName.getText().toString(),
                    clientLastName.getText().toString(),
                    clientEmail.getText().toString(),
                    clientPhone.getText().toString(),
                    "",""
            );

            skipLoginPhone = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
            String token = skipLoginPhone.getString(APP_TOKEN, "");

            Retrofit retrofit = NetworkClient.getRetrofitClient();
            Customer customer = retrofit.create(Customer.class);

            Call call = customer.addClientProfile(token, customerInformation);

            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (response.isSuccessful()){
                        CustomerInformation cus = (CustomerInformation) response.body();

                        SharedPreferences.Editor editor = skipLoginPhone.edit();
                        editor.putInt(APP_CUSTOMER_ID, cus.getCustomerId());
                        editor.apply();

                        openMainPageActivity();
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {

                }
            });


        }
    }

    private void openMainPageActivity() {
        Intent intent = new Intent(this, MainPageActivity.class);
        startActivity(intent);
    }

    private boolean validInformation() {
        if (clientFirstName.getText().toString().equals("")){
            clientFirstName.setError(getString(R.string.error_first_name));
            return false;
        }
        if (clientLastName.getText().toString().equals("")){
            clientLastName.setError(getString(R.string.error_first_name));
            return false;
        }
        return true;
    }
}
