package com.time2raise.customer.orders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.time2raise.customer.R;
import com.time2raise.customer.data.NetworkClient;
import com.time2raise.customer.data.apis.Customer;
import com.time2raise.customer.data.model.Order;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListOngoingOrdersFragment extends Fragment {


    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 1;
    private ListOngoingOrdersFragment.OnListFragmentInteractionListener mListener;
    RecyclerView recyclerView;

    public static final String APP_PREFERENCES = "SkipLoginPhone";

    public static final String APP_PREFERENCES_PHONE= "getPhone";
    public static final String APP_TOKEN = "getToken";
    SharedPreferences skipLoginPhone;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListOngoingOrdersFragment() {
    }


    @SuppressWarnings("unused")
    public static ListOngoingOrdersFragment newInstance(int columnCount) {
        ListOngoingOrdersFragment fragment = new ListOngoingOrdersFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ongoing_orders_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            loadOngoingOrders();
            //recyclerView.setAdapter(new MyListEventsRecyclerViewAdapter(DummyContent.ITEMS, mListener));
        }

        return view;
    }

    private void loadOngoingOrders(){

        skipLoginPhone = getContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String token = skipLoginPhone.getString(APP_TOKEN, "");

        Retrofit retrofit = NetworkClient.getRetrofitClient();
        Customer customer = retrofit.create(Customer.class);

        Call call = customer.getOngoingOrders(token, 25);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()){
                    List<Order> orderList = (List<Order>) response.body();
                    System.out.println("------------------ ongoing orders are loaded --------------------");
                    try {
                        System.out.println("-----------" + orderList.size() + "------------------");
                    } catch (Exception e){
                        System.out.println("EXCEPTION");
                    }
                    setAdapter(orderList);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListOngoingOrdersFragment.OnListFragmentInteractionListener) {
            mListener = (ListOngoingOrdersFragment.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(String item);
    }



    private void setAdapter(List<Order> orders){
        recyclerView.setAdapter(new MyListOngoingOrdersRecyclerViewAdapter(orders, mListener));
    }
}
