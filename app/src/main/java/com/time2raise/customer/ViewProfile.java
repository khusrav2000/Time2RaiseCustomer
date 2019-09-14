package com.time2raise.customer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.time2raise.customer.data.NetworkClient;
import com.time2raise.customer.data.apis.Customer;
import com.time2raise.customer.data.model.OrganizerInformation;
import com.time2raise.customer.data.model.RestaurantInformation;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ViewProfile extends AppCompatActivity {

    public static final String APP_PREFERENCES = "SkipLoginPhone";

    public static final String APP_PREFERENCES_PHONE= "getPhone";
    public static final String APP_TOKEN = "getToken";
    SharedPreferences skipLoginPhone;

    ImageView backgroundProfileImage;
    ImageView iconProfile;
    TextView nameProfile;
    TextView addressProfile;
    TextView aboutUs;

    Button callButton;

    final String STORAGE_URL = "https://drive.google.com/uc?export=download&id=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.profile_view);

        setSupportActionBar(myToolbar);
        //android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeButtonEnabled(true);
        //actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setTitle("");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        myToolbar.setTitle("");

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                onBackPressed();// возврат на предыдущий activity
            }
        });

        backgroundProfileImage              = findViewById(R.id.background_profile_image);
        iconProfile                         = findViewById(R.id.profile_image);
        nameProfile                         = findViewById(R.id.name_profile);
        addressProfile                      = findViewById(R.id.address_profile);
        aboutUs                             = findViewById(R.id.about_us);

        callButton                          = findViewById(R.id.call_button);

        Intent intent = getIntent();
        int organizerId = intent.getIntExtra("organizerId", -1);
        int restaurantId = intent.getIntExtra("restaurantId", -1);

        if (organizerId != -1){
            startFillOrganizerInformation(organizerId);
        } else if (restaurantId != -1){
            startFillRestaurantInformation(restaurantId);
        }
    }

    private void startFillOrganizerInformation(int organizerId) {
        skipLoginPhone = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String token = skipLoginPhone.getString(APP_TOKEN, "");

        Retrofit retrofit = NetworkClient.getRetrofitClient();
        Customer customer = retrofit.create(Customer.class);

        Call call = customer.getOrganizerById(token, organizerId);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()){
                    fillOrganizationInformation((OrganizerInformation) response.body());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    private void fillOrganizationInformation(OrganizerInformation organizerInformation) {
        Picasso picasso = Picasso.get();

        picasso.load(STORAGE_URL + organizerInformation.getBackgroundImageUrl())
                .fit()
                .centerCrop()
                .placeholder(R.drawable.no_photo)
                .error(R.drawable.no_photo)
                .into(backgroundProfileImage);

        picasso.load(STORAGE_URL + organizerInformation.getIconUrl())
                .fit()
                .centerCrop()
                .placeholder(R.drawable.photo)
                .error(R.drawable.photo)
                .into(iconProfile);

        nameProfile.setText(organizerInformation.getName());
        addressProfile.setText(organizerInformation.getAddress());
        aboutUs.setText(organizerInformation.getAbout());

    }

    private void startFillRestaurantInformation(int restaurantId){
        skipLoginPhone = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String token = skipLoginPhone.getString(APP_TOKEN, "");

        Retrofit retrofit = NetworkClient.getRetrofitClient();
        Customer customer = retrofit.create(Customer.class);

        Call call = customer.getRestaurantById(token, restaurantId);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()){
                    fillRestaurantInformation((RestaurantInformation) response.body());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    private void fillRestaurantInformation(RestaurantInformation restaurantInformation) {
        Picasso picasso = Picasso.get();

        picasso.load(STORAGE_URL + restaurantInformation.getBackgroundImageUrl())
                .fit()
                .centerCrop()
                .placeholder(R.drawable.no_photo)
                .error(R.drawable.no_photo)
                .into(backgroundProfileImage);

        picasso.load(STORAGE_URL + restaurantInformation.getIconUrl())
                .fit()
                .centerCrop()
                .placeholder(R.drawable.photo)
                .error(R.drawable.photo)
                .into(iconProfile);

        nameProfile.setText(restaurantInformation.getName());
        addressProfile.setText(restaurantInformation.getAddress());
        aboutUs.setText(restaurantInformation.getAbout());
    }
}
