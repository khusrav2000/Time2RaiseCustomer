package com.time2raise.customer;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.time2raise.customer.data.NetworkClient;
import com.time2raise.customer.data.apis.Customer;
import com.time2raise.customer.data.model.CustomerInformation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Profile extends AppCompatActivity {

    public static final String APP_PREFERENCES = "SkipLoginPhone";
    public static final String APP_PREFERENCES_PHONE= "getPhone";
    public static final String APP_TOKEN = "getToken";
    public static final String APP_CUSTOMER_ID = "id";
    SharedPreferences skipLoginPhone;

    Button saveProfileInformation;
    EditText clientPhone;
    Button logout;
    EditText clientFirstName;
    EditText clientLastName;
    EditText clientEmail;

    ImageView iconProfile;

    final String STORAGE_URL = "https://drive.google.com/uc?export=download&id=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        saveProfileInformation      = findViewById(R.id.save_profile_information);
        clientPhone                 = findViewById(R.id.edit_client_phone);
        clientFirstName             = findViewById(R.id.edit_client_first_name);
        clientLastName              = findViewById(R.id.edit_client_last_name);
        clientEmail                 = findViewById(R.id.edit_client_email);
        iconProfile                 = findViewById(R.id.profile_icon);

        logout = findViewById(R.id.logout);


        skipLoginPhone  = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String phone    = skipLoginPhone.getString(APP_PREFERENCES_PHONE, "");
        String token    = skipLoginPhone.getString(APP_TOKEN, "");
        int id          = skipLoginPhone.getInt(APP_CUSTOMER_ID, -1);

        Retrofit retrofit = NetworkClient.getRetrofitClient();
        Customer customer = retrofit.create(Customer.class);

        Call call = customer.getCustomerProfileById(token);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()){
                    fillFields((CustomerInformation) response.body());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });

        clientPhone.setText(phone);


        saveProfileInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainPageActivity();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLogout();
            }
        });
    }

    private void fillFields(CustomerInformation customerInformation) {
        clientFirstName.setText(customerInformation.getFirstName());
        clientLastName.setText(customerInformation.getLastName());
        clientEmail.setText(customerInformation.getEmail());

        Picasso picasso = Picasso.get();
        picasso.load(STORAGE_URL + customerInformation.getIconUrl())
                .fit()
                .centerCrop()
                .placeholder(R.drawable.photo)
                .error(R.drawable.photo)
                .into(iconProfile);
    }

    private void startLogout() {
        skipLoginPhone = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String token = skipLoginPhone.getString(APP_TOKEN, "");

        Retrofit retrofit = NetworkClient.getRetrofitClient();
        Customer customer = retrofit.create(Customer.class);

        Call call = customer.logout(token);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()){
                    SharedPreferences.Editor editor = skipLoginPhone.edit();
                    editor.clear();
                    editor.apply();
                    startVerificationCode();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    private void startVerificationCode() {
        Intent intent = new Intent(this, VerifyPhoneNumber.class);
        startActivity(intent);
    }

    private void startMainPageActivity() {
        Intent intent = new Intent(this, MainPageActivity.class);
        startActivity(intent);
    }
}
