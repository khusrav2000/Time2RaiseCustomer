package com.time2raise.customer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class PlaceAndOrderFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    LinearLayout[] foods = new LinearLayout[10];

    public PlaceAndOrderFragment() {
        // Required empty public constructor
    }
    public static PlaceAndOrderFragment newInstance(String param1, String param2) {
        PlaceAndOrderFragment fragment = new PlaceAndOrderFragment();
        Bundle args = new Bundle();
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
        View view = inflater.inflate(R.layout.fragment_place_and_order, container, false);


        foods[0] = view.findViewById(R.id.food_0);
        foods[1] = view.findViewById(R.id.food_1);
        foods[2] = view.findViewById(R.id.food_2);
        foods[3] = view.findViewById(R.id.food_3);
        foods[4] = view.findViewById(R.id.food_4);
        foods[5] = view.findViewById(R.id.food_5);
        foods[6] = view.findViewById(R.id.food_6);
        foods[7] = view.findViewById(R.id.food_7);
        foods[8] = view.findViewById(R.id.food_8);
        foods[9] = view.findViewById(R.id.food_9);

        for (int i = 0 ; i < 10 ; i++){
            foods[i].setOnClickListener(this);
        }


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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.food_0:
                mListener.openGetFoodByCategoryId(0);
                break;
            case R.id.food_1:
                mListener.openGetFoodByCategoryId(1);
                break;
            case R.id.food_2:
                mListener.openGetFoodByCategoryId(2);
                break;
            case R.id.food_3:
                mListener.openGetFoodByCategoryId(3);
                break;
            case R.id.food_4:
                mListener.openGetFoodByCategoryId(4);
                break;
            case R.id.food_5:
                mListener.openGetFoodByCategoryId(5);
                break;
            case R.id.food_6:
                mListener.openGetFoodByCategoryId(6);
                break;
            case R.id.food_7:
                mListener.openGetFoodByCategoryId(7);
                break;
            case R.id.food_8:
                mListener.openGetFoodByCategoryId(8);
                break;
            case R.id.food_9:
                mListener.openGetFoodByCategoryId(9);
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        void openGetFoodByCategoryId(int categoryId);
    }
}
