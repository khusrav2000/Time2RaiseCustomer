package com.time2raise.customer.orders;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.time2raise.customer.R;

import java.nio.Buffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link} and makes a call to the
 * specified {@link ListOngoingOrdersFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyListPastOrdersRecyclerViewAdapter extends RecyclerView.Adapter<MyListPastOrdersRecyclerViewAdapter.ViewHolder> {


    private final List<String > mValues;

    private final ListPastOrdersFragment.OnListFragmentInteractionListener mListener;


    String StorageUrl = "https://drive.google.com/uc?export=download&id=";

    View getContexts;


    public MyListPastOrdersRecyclerViewAdapter(List<String> requests, ListPastOrdersFragment.OnListFragmentInteractionListener listener) {
        mValues = requests;
        mListener = listener;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Скопируем layout, которое будем помещать в каждую строку ListView.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_past_order_line, parent, false);
        getContexts = view;
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

    }

    // Получения количество event-ов с списке.
    @Override
    public int getItemCount() {
        if (mValues != null)
            return mValues.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;


        public String mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

        }

        @Override
        public String toString() {
            return "2";
        }
    }
}
