package com.time2raise.customer;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.time2raise.customer.events.ListEventsFragment;
import com.time2raise.customer.orders.ListOngoingOrdersFragment;
import com.time2raise.customer.orders.ListPastOrdersFragment;
import com.time2raise.customer.orders.OrdersFragment;

public class MainPageActivity extends AppCompatActivity implements
        OrdersFragment.OnFragmentInteractionListener,
        ListPastOrdersFragment.OnListFragmentInteractionListener,
        ListOngoingOrdersFragment.OnListFragmentInteractionListener,

        ListEventsFragment.OnListFragmentInteractionListener {
    private ImageView profileIcon;

    Fragment listEventsFragment = new ListEventsFragment();
    Fragment ordersFragment = new OrdersFragment();



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
    public void onListFragmentInteraction(String item) {

    }
}
