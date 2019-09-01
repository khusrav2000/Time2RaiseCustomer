package com.time2raise.customer.events;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.time2raise.customer.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link} and makes a call to the
 * specified {@link ListEventsFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyListEventsRecyclerViewAdapter extends RecyclerView.Adapter<MyListEventsRecyclerViewAdapter.ViewHolder> {

    // Список event-ов, которые мы получаем из сервера.
    // Events - Это модель для помещения в него получаемых данных из сервера.
    private final List<String> mValues;

    private final ListEventsFragment.OnListFragmentInteractionListener mListener;

    // Ссылка на хранилище фотографий этого проета.
    // Фотографии находяться в GoogleDrive-е.
    String StorageUrl = "https://drive.google.com/uc?export=download&id=";

    View getContexts;

    public MyListEventsRecyclerViewAdapter(List<String> items, ListEventsFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Скопируем layout, которое будем помещать в каждую строку ListView.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_event_line, parent, false);
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
            return "";
        }
    }
}
