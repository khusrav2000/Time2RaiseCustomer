package com.time2raise.customer;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.time2raise.customer.data.NetworkClient;
import com.time2raise.customer.data.apis.Customer;
import com.time2raise.customer.data.model.EventDetailed;
import com.time2raise.customer.data.model.EventInf;
import com.time2raise.customer.data.model.ResFood;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PickFoodsByCategoryIdFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PickFoodsByCategoryIdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PickFoodsByCategoryIdFragment extends Fragment {

    int categoryId;
    int eventId;
    String token;

    public static final String APP_PREFERENCES = "SkipLoginPhone";

    public static final String APP_PREFERENCES_PHONE= "getPhone";
    public static final String APP_TOKEN = "getToken";
    SharedPreferences skipLoginPhone;

    List<ResFood> foods;

    private OnFragmentInteractionListener mListener;

    public PickFoodsByCategoryIdFragment() {
        // Required empty public constructor
    }


    public PickFoodsByCategoryIdFragment newInstance(int categoryId, int eventId) {
        PickFoodsByCategoryIdFragment fragment = new PickFoodsByCategoryIdFragment();
        Bundle args = new Bundle();
        this.categoryId = categoryId;
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
        View view = inflater.inflate(R.layout.fragment_pick_foods_by_category_id, container, false);
        skipLoginPhone = getContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        token = skipLoginPhone.getString(APP_TOKEN, "");

        System.out.println("--------------" + categoryId);
        getRestaurantId();
        return view;
    }

    private void loadFoods(int restaurantId) {

        Retrofit retrofit = NetworkClient.getRetrofitClient();
        Customer customer = retrofit.create(Customer.class);

        Call call = customer.getFoodsByResIdAndCategory(token, restaurantId, categoryId);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                System.out.println("RESSS -    - - - -- - - -" + response.code());
                if (response.isSuccessful()){
                    List<ResFood> resFoods = (List<ResFood>) response.body();
                    for (ResFood resFood : resFoods) {
                        System.out.println("--------------" + resFood.getFoodName());
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    private void getRestaurantId() {
        System.out.println("Evet id: " + eventId);
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        Customer customer = retrofit.create(Customer.class);

        Call call = customer.getEventById(token, eventId);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()){
                    EventDetailed eventInformation = (EventDetailed) response.body();
                    loadFoods(eventInformation.getInitId());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
