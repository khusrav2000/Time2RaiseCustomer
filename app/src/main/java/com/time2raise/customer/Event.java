package com.time2raise.customer;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.time2raise.customer.data.NetworkClient;
import com.time2raise.customer.data.apis.Customer;
import com.time2raise.customer.data.model.EventInformation;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Call;

import com.time2raise.customer.data.model.*;


public class Event extends AppCompatActivity implements View.OnClickListener {


    Button viewOrganizerProfile;

    int eventId;
    int organizerId;
    int restaurantId;

    TextView eventName;
    TextView eventDate;
    TextView eventStartEndTime;
    ImageView eventImage;
    TextView eventAbout;

    final String STORAGEURL = "https://drive.google.com/uc?export=download&id=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        viewOrganizerProfile    = findViewById(R.id.organizer_view_profile);
        eventName               = findViewById(R.id.view_event_name);
        eventDate               = findViewById(R.id.view_event_date);
        eventStartEndTime       = findViewById(R.id.view_event_start_end_time);
        eventImage              = findViewById(R.id.view_event_image);
        eventAbout              = findViewById(R.id.view_event_about);


        viewOrganizerProfile.setOnClickListener(this);

        Intent intent = getIntent();

        eventId = intent.getIntExtra("eventId", -1);
        organizerId = intent.getIntExtra("organizerId", -1);
        restaurantId = intent.getIntExtra("restaurantId", -1);

        Retrofit retrofit = NetworkClient.getRetrofitClient();
        Customer customer = retrofit.create(Customer.class);

        Call call = customer.getEventById(eventId);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful())
                    setEventInformation((EventInformation) response.body());
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });



    }

    private void setEventInformation(EventInformation event) {
        eventName.setText(event.getName());
        eventDate.setText(event.getDate());
        eventStartEndTime.setText(event.getStart() + event.getEnd());
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
            picasso.load(STORAGEURL + photoUrl)
                    .fit()
                    .centerCrop()
                    .into(eventImage);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.organizer_view_profile:
                startViewProfileOrganizer();
                break;
        }
    }

    private void startViewProfileOrganizer() {
        Intent intent = new Intent(this, ViewProfile.class);
        startActivity(intent);
    }
}
