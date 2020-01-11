package com.time2raise.customer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.time2raise.customer.data.NetworkClient;
import com.time2raise.customer.data.apis.Customer;
import com.time2raise.customer.data.model.CustomerInformation;
import com.time2raise.customer.data.room.OrderRepository;
import com.time2raise.customer.data.room.OrderToCart;
import com.time2raise.customer.data.room.dao.OrderToCartDao;

import java.util.IllegalFormatCodePointException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Event extends AppCompatActivity implements
        PlaceAndOrderFragment.OnFragmentInteractionListener,
        EventInformation.OnFragmentInteractionListener,
        PickFoodsByCategoryIdFragment.OnFragmentInteractionListener,
        CartFragment.OnFragmentInteractionListener{


    ImageView iconProfile;

    int eventId;
    int categoryId;
    ImageView cart;
    TextView countFoodsCart;
    TextView nameEventTop;


    final int EVENT_INFORMATION_NUMBER = 1;
    final int FOOD_CATEGORY_NUMBER = 2;
    final int PICK_FOOD_NUMBER = 3;

    int backFragment = 1;

    final String STORAGE_URL = "https://drive.google.com/uc?export=download&id=";
    SharedPreferences skipLoginPhone;

    public static final String APP_PREFERENCES = "SkipLoginPhone";
    public static final String APP_TOKEN = "getToken";


    PlaceAndOrderFragment placeAndOrderFragment = new PlaceAndOrderFragment();
    com.time2raise.customer.EventInformation eventInformation = new com.time2raise.customer.EventInformation();
    PickFoodsByCategoryIdFragment pickFoodsByCategoryIdFragment = new PickFoodsByCategoryIdFragment();
    CartFragment cartFragment = new CartFragment();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        addEventInformationFragment();
        iconProfile         = findViewById(R.id.icon_profile);
        cart                = findViewById(R.id.cart);
        countFoodsCart      = findViewById(R.id.count_foods_cart);
        nameEventTop        = findViewById(R.id.name_event_top);


        iconProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfile();
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
                            .into(iconProfile);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
        Intent intent = getIntent();
        eventId = intent.getIntExtra("eventId", -1);
        eventInformation.newInstance(eventId);


        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startViewCart();
            }
        });
        OrderRepository orderRepository = new OrderRepository(getApplication());
        new GetFoodsAndSetCount(orderRepository, eventId).execute();

    }

    private class GetFoodsAndSetCount extends AsyncTask<Void, Void, List<OrderToCart>> {

        private OrderRepository orderRepository;
        public int eventId;

        GetFoodsAndSetCount(OrderRepository orderRepos, int eventId) {
            orderRepository = orderRepos;
            this.eventId = eventId;
        }



        @Override
        protected List<OrderToCart> doInBackground(Void... params) {
            //List<OrderToCart> orderToCarts = orderRepository.getOrdersByEventId(eventId);

            return orderRepository.getOrdersByEventId(eventId);
        }

        @Override
        protected void onPostExecute(List<OrderToCart> orderToCarts) {
            setCountFoods(orderToCarts);
        }
    }

    private void setCountFoods(List<OrderToCart> orderToCarts) {
        System.out.println("Count!!!!!!!!!!!!!!!!!!!!!!!!!!!11");
        if(orderToCarts.size() > 0) {
            countFoodsCart.setText(String.valueOf(orderToCarts.size()));
            countFoodsCart.setVisibility(View.VISIBLE);
        } else {
            countFoodsCart.setVisibility(View.GONE);
        }
    }

    private void startViewCart() {
        cartFragment.newInstance(eventId, 1);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.event_information, cartFragment);
        transaction.commit();
        cart.setVisibility(View.GONE);
        nameEventTop.setText("Cart");
        countFoodsCart.setVisibility(View.GONE);
    }



    private void openProfile() {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    private void addEventInformationFragment() {
        backFragment = 1;
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
        backFragment = 2 ;
        System.out.println("-------------- Rabotaet ---------------------");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.event_information, placeAndOrderFragment);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void goToPlaceAndOrder() {
        startPlaceAndOrder();
    }

    @Override
    public void setCountFoodsCart() {
        System.out.println("--------------Count---------------");
        OrderRepository orderRepository = new OrderRepository(getApplication());
        new GetFoodsAndSetCount(orderRepository, eventId).execute();
    }

    @Override
    public void goToBackFragment() {
        if (backFragment == EVENT_INFORMATION_NUMBER){
            addEventInformationFragment();
        } else if(backFragment == FOOD_CATEGORY_NUMBER){
            startPlaceAndOrder();
        } else if(backFragment == PICK_FOOD_NUMBER){
            openGetFoodByCategoryId(categoryId);
        }
        cart.setVisibility(View.VISIBLE);
        nameEventTop.setText("events");
        countFoodsCart.setVisibility(View.VISIBLE);
        OrderRepository orderRepository = new OrderRepository(getApplication());
        new GetFoodsAndSetCount(orderRepository, eventId).execute();
    }

    @Override
    public void openGetFoodByCategoryId(int categoryId){
        backFragment = 3;
        this.categoryId = categoryId;
        pickFoodsByCategoryIdFragment.newInstance(categoryId, eventId);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.event_information, pickFoodsByCategoryIdFragment);
        transaction.commit();
    }

    @Override
    public void goToEventInformationFragment() {
        addEventInformationFragment();
    }
}
