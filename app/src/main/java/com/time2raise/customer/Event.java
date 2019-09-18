package com.time2raise.customer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class Event extends AppCompatActivity implements
        PlaceAndOrderFragment.OnFragmentInteractionListener,
        EventInformation.OnFragmentInteractionListener,
        PickFoodsByCategoryIdFragment.OnFragmentInteractionListener {


    ImageView iconProfile;

    int eventId;


    PlaceAndOrderFragment placeAndOrderFragment = new PlaceAndOrderFragment();
    com.time2raise.customer.EventInformation eventInformation = new com.time2raise.customer.EventInformation();
    PickFoodsByCategoryIdFragment pickFoodsByCategoryIdFragment = new PickFoodsByCategoryIdFragment();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        addEventInformationFragment();
        iconProfile         = findViewById(R.id.icon_profile);

        iconProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfile();


            }
        });

        Intent intent = getIntent();
        eventId = intent.getIntExtra("eventId", -1);
        eventInformation.newInstance(eventId);

    }

    private void openProfile() {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    private void addEventInformationFragment() {
        System.out.println("-------------- Rabotaet Replace Event Inf ---------------------");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.event_information, eventInformation);
        transaction.commit();
    }



    @Override
    public void startViewProfileOrganizer(int organizerId) {
        Intent intent = new Intent(this, ViewProfile.class);
        intent.putExtra("organizerId", organizerId);
        startActivity(intent);
    }

    public void startViewProfileRestaurant(int restaurantId){
        Intent intent = new Intent(this, ViewProfile.class);
        intent.putExtra("restaurantId", restaurantId);
        startActivity(intent);
    }

    @Override
    public void startPlaceAndOrder() {
        System.out.println("-------------- Rabotaet ---------------------");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.event_information, placeAndOrderFragment);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void openGetFoodByCategoryId(int categoryId){
        pickFoodsByCategoryIdFragment.newInstance(categoryId, eventId);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.event_information, pickFoodsByCategoryIdFragment);
        transaction.commit();
    }
}
