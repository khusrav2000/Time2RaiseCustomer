package com.time2raise.customer;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.time2raise.customer.data.NetworkClient;
import com.time2raise.customer.data.apis.Customer;
import com.time2raise.customer.data.model.OrganizerInformation;
import com.time2raise.customer.data.model.Photo;
import com.time2raise.customer.data.model.RestaurantInformation;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class EventInformation extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    public static final String APP_PREFERENCES = "SkipLoginPhone";

    public static final String APP_PREFERENCES_PHONE= "getPhone";
    public static final String APP_TOKEN = "getToken";
    SharedPreferences skipLoginPhone;

    int eventId;

    Button viewOrganizerProfile;
    Button viewRestaurantViewProfile;
    TextView eventName;
    TextView eventDate;
    TextView eventStartEndTime;
    TextView eventAbout;
    ImageView eventImage;
    Button placeAndOrder;

    ImageView organizerIconProfile;
    TextView organizerName;
    TextView organizerAddress;

    ImageView restaurantIconProfile;
    TextView restaurantName;
    TextView restaurantAddress;

    Retrofit retrofit = NetworkClient.getRetrofitClient();
    Customer customer = retrofit.create(Customer.class);

    int organizerId;
    int restaurantId;

    final String STORAGE_URL = "https://drive.google.com/uc?export=download&id=";

    String token;

    public EventInformation() {
        // Required empty public constructor
    }


    public EventInformation newInstance(int eventId) {
        EventInformation fragment = new EventInformation();
        Bundle args = new Bundle();

        this.eventId = eventId;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_information, container, false);

        viewOrganizerProfile        = view.findViewById(R.id.organizer_view_profile);
        viewRestaurantViewProfile   = view.findViewById(R.id.restaurant_view_profile);

        eventName                   = view.findViewById(R.id.view_event_name);
        eventDate                   = view.findViewById(R.id.view_event_date);
        eventStartEndTime           = view.findViewById(R.id.view_event_start_end_time);
        eventImage                  = view.findViewById(R.id.view_event_image);
        eventAbout                  = view.findViewById(R.id.view_event_about);
        placeAndOrder               = view.findViewById(R.id.place_and_order_button);

        organizerIconProfile        = view.findViewById(R.id.organizer_profile_image);
        organizerName               = view.findViewById(R.id.organizer_name);
        organizerAddress            = view.findViewById(R.id.organizer_address);

        restaurantIconProfile       = view.findViewById(R.id.restaurant_profile_image);
        restaurantName              = view.findViewById(R.id.restaurant_name);
        restaurantAddress           = view.findViewById(R.id.restaurant_address);

        skipLoginPhone = getContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        token = skipLoginPhone.getString(APP_TOKEN, "");



        Call call = customer.getEventById(token, eventId);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    setEventInformation((com.time2raise.customer.data.model.EventInformation) response.body());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });

        placeAndOrder.setOnClickListener(this);
        viewOrganizerProfile.setOnClickListener(this);
        viewRestaurantViewProfile.setOnClickListener(this);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void setEventInformation(com.time2raise.customer.data.model.EventInformation event) {
        eventName.setText(event.getName());
        eventDate.setText(event.getDate());
        eventStartEndTime.setText(event.getStart() + event.getEnd());
        eventAbout.setText(event.getAbout());

        Picasso picasso = Picasso.get();

        List<Photo> photos = event.getPhoto();

        String photoUrl = null;
        if (photos != null){
            for (Photo photo : photos){
                if (photo.isMain()){
                    photoUrl = photo.getUrl();
                }
            }
        }

        if (photoUrl != null)
            picasso.load(STORAGE_URL + photoUrl)
                    .fit()
                    .centerCrop()
                    .into(eventImage);


        // Получения информации об организаторе по id.
        Call callOrg = customer.getOrganizerById(token, event.getOrgId());
        callOrg.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                System.out.println("Organization Code - " + response.code());
                if (response.isSuccessful()) {
                    setOrganizerInformation((com.time2raise.customer.data.model.OrganizerInformation) response.body());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });

        // Получения информации об ресторане по id.
        Call callRes = customer.getRestaurantById(token, event.getRequestId());
        callRes.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                System.out.println(response.code());
                if (response.isSuccessful()) {
                    setRestaurantInformation((com.time2raise.customer.data.model.RestaurantInformation) response.body());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });

    }

    private void setRestaurantInformation(RestaurantInformation restaurantInformation) {
        restaurantId = restaurantInformation.getOrgId();

        Picasso picasso = Picasso.get();
        picasso.load(STORAGE_URL + restaurantInformation.getIconUrl())
                .fit()
                .centerCrop()
                .placeholder(R.drawable.photo)
                .error(R.drawable.photo)
                .into(restaurantIconProfile);

        restaurantName.setText(restaurantInformation.getName());
        restaurantAddress.setText(restaurantInformation.getAddress());
    }

    private void setOrganizerInformation(OrganizerInformation organizerInformation) {
        organizerId = organizerInformation.getInitId();

        System.out.println("----------Organization Information are loaded!--------");
        Picasso picasso = Picasso.get();
        picasso.load(STORAGE_URL + organizerInformation.getIconUrl())
                .fit()
                .centerCrop()
                .placeholder(R.drawable.photo)
                .error(R.drawable.photo)
                .into(organizerIconProfile);

        organizerName.setText(organizerInformation.getName());
        organizerAddress.setText(organizerInformation.getAddress());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.place_and_order_button:
                mListener.startPlaceAndOrder();
                break;
            case R.id.organizer_view_profile:
                mListener.startViewProfileOrganizer(organizerId);
                break;
            case R.id.restaurant_view_profile:
                mListener.startViewProfileRestaurant(restaurantId);
                break;
        }
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);

        // Все нижеперечисленные интерфейсы буду реализованы в классе Event.
        void startPlaceAndOrder();      // Интерфейс для запуска выбора категорий еди.
        void startViewProfileOrganizer(int organizationId); // Интерфейс для запуска профиля организатора.
        void startViewProfileRestaurant(int restaurantId); // Интерфейс для запуска профиля ресторана
    }
}
