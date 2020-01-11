package com.time2raise.customer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;
import com.time2raise.customer.data.NetworkClient;
import com.time2raise.customer.data.apis.Customer;
import com.time2raise.customer.data.model.CustomerInformation;
import com.time2raise.customer.data.model.EventInf;
import com.time2raise.customer.events.ListEventsFragment;
import com.time2raise.customer.orders.ListOngoingOrdersFragment;
import com.time2raise.customer.orders.ListPastOrdersFragment;
import com.time2raise.customer.orders.OrdersFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainPageActivity extends AppCompatActivity implements
        OrdersFragment.OnFragmentInteractionListener,
        ListPastOrdersFragment.OnListFragmentInteractionListener,
        ListOngoingOrdersFragment.OnListFragmentInteractionListener,

        ListEventsFragment.OnListFragmentInteractionListener {

    private ImageView profileIcon;

    Fragment listEventsFragment = new ListEventsFragment();
    Fragment ordersFragment = new OrdersFragment();

    final String STORAGE_URL = "https://drive.google.com/uc?export=download&id=";
    SharedPreferences skipLoginPhone;

    public static final String APP_PREFERENCES = "SkipLoginPhone";
    public static final String APP_TOKEN = "getToken";


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_events:
                    System.out.println("EVENTS -------------------- OMAD");
                    transaction.replace(R.id.fragment, listEventsFragment);
                    transaction.commit();
                    return true;
                case R.id.navigation_orders:
                    System.out.println("ORDERSSS -------------- OMAD");
                    transaction.replace(R.id.fragment, ordersFragment);
                    transaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        profileIcon = findViewById(R.id.icon_profile);
        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProfileActivity();
            }
        });

        skipLoginPhone  = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String token    = skipLoginPhone.getString(APP_TOKEN, "");

        Retrofit retrofit = NetworkClient.getRetrofitClient();
        Customer customer = retrofit.create(Customer.class);

        Call call = customer.getCustomerProfileById(token);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()){
                    CustomerInformation customerInformation = ((CustomerInformation) response.body());
                    Picasso picasso = Picasso.get();
                    picasso.load(STORAGE_URL + customerInformation.getIconUrl())
                            .fit()
                            .error(R.drawable.photo)
                            .placeholder(R.drawable.icon_loading)
                            .into(profileIcon);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });

        final FrameLayout searchFilter = findViewById(R.id.search_filter);
        findViewById(R.id.image_search_filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICK !!!!!!!!!!!!!!!!!!!!!!!!!!!!! ");
                if (searchFilter.getVisibility() == View.GONE) {
                    searchFilter.setVisibility(View.VISIBLE);
                } else {
                    searchFilter.setVisibility(View.GONE);
                }
            }
        });

        final CheckBox searchByName = findViewById(R.id.search_by_name);
        final CheckBox searchByZipCode = findViewById(R.id.search_by_zip_code);
        searchByName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SearchView searchView = findViewById(R.id.search);
                searchView.setQuery("", false);
                if (isChecked){
                    searchByZipCode.setChecked(false);
                } else {
                    searchByZipCode.setChecked(true);
                }
            }
        });
        searchByZipCode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SearchView searchView = findViewById(R.id.search);
                searchView.setQuery("", false);
                if (isChecked){
                    searchByName.setChecked(false);
                } else {
                    searchByName.setChecked(true);
                }
            }
        });


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, listEventsFragment);
        transaction.commit();
    }

    private void startProfileActivity() {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(EventInf item) {
        Intent intent = new Intent(this, Event.class);
        intent.putExtra("eventId", item.getEventId());
        startActivity(intent);
    }

    @Override
    public void onListFragmentInteraction(String item) {

    }
}
