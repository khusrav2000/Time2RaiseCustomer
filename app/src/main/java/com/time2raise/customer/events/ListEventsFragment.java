package com.time2raise.customer.events;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.time2raise.customer.R;
import com.time2raise.customer.data.NetworkClient;
import com.time2raise.customer.data.apis.Customer;
import com.time2raise.customer.data.model.EventInf;
import com.time2raise.customer.data.model.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListEventsFragment extends Fragment  {


    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    RecyclerView recyclerView;

    public static final String APP_PREFERENCES = "SkipLoginPhone";

    public static final String APP_PREFERENCES_PHONE= "getPhone";
    public static final String APP_TOKEN = "getToken";
    SharedPreferences skipLoginPhone;

    String token;
    String filterText;

    MyListEventsRecyclerViewAdapter adapter;

    public ListEventsFragment() {
    }


    @SuppressWarnings("unused")
    public static ListEventsFragment newInstance(int columnCount) {
        ListEventsFragment fragment = new ListEventsFragment();
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
        View view = inflater.inflate(R.layout.fragment_events_list, container, false);

        // Set the adapter
        if (view.findViewById(R.id.events_list) instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view.findViewById(R.id.events_list);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            loadEvents();
            //recyclerView.setAdapter(new MyListEventsRecyclerViewAdapter(DummyContent.ITEMS, mListener));
        }

        setSearchListener();
        return view;
    }

    public void setSearchListener(){
        SearchView searchView = getActivity().findViewById(R.id.search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterItems(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterItems(newText);
                System.out.println("IT IS WORK _______________--------------");
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                filterItems("");
                System.out.println("Close!!!!");
                return false;
            }
        });
    }

    private void filterItems(String text) {
        filterText = text;
        if (adapter != null ) {
            adapter.getFilter().filter(filterText);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
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


    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(EventInf item);
    }



    private void loadEvents(){
        skipLoginPhone = getContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        token = skipLoginPhone.getString(APP_TOKEN, "");


        // Загрузка списка event-ов с сервера.
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        Customer customer = retrofit.create(Customer.class);

        // TODO: Поменять лимит для количество event-ов.
        Call call = customer.getEvents(token,100);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                System.out.println("----------------------" + response.code());
                if (response.isSuccessful()) {
                    List<EventInf> events = (List<EventInf>) response.body();
                    System.out.println("----------------Events are loaded ---------------------------");
                    setAdapter(events);
                } else if(response.code() == 400){
                    updateToken();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });

    }

    private void updateToken(){
        skipLoginPhone = getContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        Retrofit retrofit = NetworkClient.getRetrofitClient();
        Customer customer = retrofit.create(Customer.class);
        Call call = customer.getNewToken(token);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()){
                    Message message = (Message) response.body();
                    String newToken = message.getMassage();
                    SharedPreferences.Editor editor = skipLoginPhone.edit();
                    editor.putString(APP_TOKEN, newToken);
                    editor.apply();
                    loadEvents();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });

    }

    private void setAdapter(List<EventInf> events){
        adapter = new MyListEventsRecyclerViewAdapter(getActivity(), events, mListener);
        if (filterText != null){
            adapter.getFilter().filter(filterText);
        }
        recyclerView.setAdapter(adapter);
    }
}
